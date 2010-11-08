#!/usr/bin/python
# encoding: utf-8

import web

# web config
# web.config.debug = True

# app config
from urls import urls
app = web.application(urls, autoreload=True)

# db config
db = web.database(
    dbn='mysql',
    host='127.0.0.1',
    user='root',
    pw='123456',
    db='sample'
)

# form config
form = web.form

# session config
if web.config.get('_session') is None:
    session = web.session.Session(app, web.session.DiskStore('session'))
    web.config._session = session
else:
    session = web.config._session

# run app
if __name__ == "__main__":
    app.run()
