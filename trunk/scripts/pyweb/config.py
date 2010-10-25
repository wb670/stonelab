#!/usr/bin/python
# encoding: utf-8

import sys,web
from urls import urls

# web config
# web.config.debug = True

# app config
app = web.application(urls,globals())

# db config
db = web.database(
        dbn='mysql',
        host='127.0.0.1',
        user='root',
        pw='123456',
        db='sample')

# session config
session = web.session.Session(
        app,
        web.session.DiskStore('session'))

# render config
# render = web.template.render('templates/')
