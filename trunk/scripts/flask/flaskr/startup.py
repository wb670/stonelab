#!/bin/python

from controllers import blog
from controllers import home
from flask import Flask

DATABASE = '/tmp/flaskr.db'
DEBUG = True
SECRET_KEY = 'key'
USERNAME = 'admin'
PASSWORD = 'default'

app = Flask(__name__)
app.debug = True
app.secret_key = 'A0Zr98j/3yX R~XHH!jmN]LWX/,?RT'

app.register_blueprint(home.home)
app.register_blueprint(blog.blog)

#init database
def init():
    fp = open('zdocs/schema.sql');
    sql = fp.read()
    fp.close()
    
    db = blog.connect_db()
    db.executescript(sql)
    db.commit()
    db.close()
    print 'database init successfully.'

init()    
app.run()
