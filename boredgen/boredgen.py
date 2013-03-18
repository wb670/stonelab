#!/usr/bin/python

import os, sys
import MySQLdb
from MySQLdb.cursors import CursorStoreResultMixIn, BaseCursor
from jinja2 import Environment, FileSystemLoader

class Pojo(dict):

    def __getattr__(self, key):
        return self[key]

    def __setattr__(self, key, value):
        self[key] = value

    def __delattr__(self, key):
        del self[key]

class CursorX(CursorStoreResultMixIn, BaseCursor):

    _fetch_type = 1

    def fetchone(self):
        return Pojo(CursorStoreResultMixIn.fetchone(self))

    def fetchmany(self, size=None):
        return [Pojo(r) for r in CursorStoreResultMixIn.fetchmany(self, size)]

    def fetchall(self):
        return [Pojo(r) for r in CursorStoreResultMixIn.fetchall(self)]

SQL_TYPES = (
        ('int', 'bigint'),
        ('varchar', 'char'),
        ('datetime'),
)
JAVA_TYPES = (
        'long',
        'String',
        'Date',
)
def mapper(type):
    for i, t in enumerate(SQL_TYPES):
        if type in t:
            return JAVA_TYPES[i]
    raise Exception('mapper error. type not found type is %s' % (type))

DATA_SOURCE = {
    'host'          : 'localhost',
    'user'          : 'einfo',
    'passwd'        : 'einfo',
    'db'            : 'einfo',
    'charset'       : 'utf8',
    'cursorclass'   : CursorX
}

'''
ctx.dir
ctx.pkg
ctx.datasource
ctx.con
ctx.t_env
ctx.t_table
ctx.tables
    name
    fields
    model_name
    model_attributes
'''
ctx = Pojo()

def select(sql, param=None):
    if not 'con' in ctx:
        return None
    cursor = ctx.con.cursor()
    cursor.execute(sql, param)
    ret = cursor.fetchall()
    cursor.close()
    return ret

def render(template, file=None, encoding='utf-8'):
    t = ctx.t_env.get_template(template)
    if not t:
        return
    ret = t.render(ctx = ctx)
    if file:
        open(file, 'w').write(ret)
    return ret

def _ensure_dir(dir):
    if not os.path.exists(dir):
        os.makedirs(dir)

def _get_base_dir():
    return '%s/%s' % (ctx.dir, ctx.pkg.replace('.', '/'))

def init(dir, pkg):
    ctx.dir = dir
    ctx.pkg = pkg

    ctx.datasource = DATA_SOURCE
    ctx.con = MySQLdb.connect(**ctx.datasource)
    ctx.tables = Pojo()
    ctx.models = Pojo()
    
    ctx.t_env = Environment(loader = FileSystemLoader('.'))
    def capitalizex(value):
        if not value: return None
        return value[0].capitalize() + value[1:]
    ctx.t_env.filters['capitalizex'] = capitalizex


def init_data():
    tables = [t.Tables_in_einfo for t in select('show tables')]
    for table in tables:
        init_table(table)
        init_model(table)

def init_table(name):
    table= Pojo()
    #table name
    table.name = name
    #table fields
    table.fields = []
    for i in select('desc %s' % (name)):
        field = Pojo()
        field.name = i.Field
        field.type = i.Type[0: i.Type.index('(')] if '(' in i.Type else i.Type
        field.is_pri = 'pri' == i.Key.lower()
        table.fields.append(field)
    ctx.tables[name] = table

def init_model(name):
    table = ctx.tables[name]
    table.model_name = ''.join([i.capitalize() for i in table.name.split('_')])
    table.model_attributes = [] 
    for f in table.fields:
        attr = Pojo() 
        attr.name = ''.join([v if i == 0 else v.capitalize() for i, v in enumerate(f.name.split('_'))])
        attr.type = mapper(f.type)
        table.model_attributes.append(attr)

def gen_do(name):
    dir = '%s/%s' % (_get_base_dir(), 'dataobject')
    _ensure_dir(dir)
    render('tpl_do.tpl', '%s/%s.java' % (dir, ctx.tables[name].model_name))

def destroy():
    if ctx.con:
        ctx.con.close()
    ctx.clear()
