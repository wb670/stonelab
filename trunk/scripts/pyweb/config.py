#!/usr/bin/python
# encoding: utf-8

import sys,web
from urls import urls

# web config
# web.config.debug = False

# app config
app = web.application(urls,globals())

# db config
db = web.database(
        dbn='mysql',
        host='127.0.0.1',
        user='root',
        pw='123456',
        db='sample')

# form config
form = web.form

# session config
if web.config.get('_session') is None:
    session = web.session.Session(app, web.session.DiskStore('session'))
    web.config._session = session
else:
    session = web.config._session

# render config
# render = web.template.render('templates/')
