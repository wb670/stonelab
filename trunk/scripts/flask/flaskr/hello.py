#!/bin/python

from flask import Flask, render_template, session

app = Flask(__name__)
app.debug = True
app.secret_key = 'A0Zr98j/3yX R~XHH!jmN]LWX/,?RT'

@app.before_request
def b1():
    print 'b1'

@app.after_request
def a1(resp):
    print 'a1'
    return resp

@app.route('/hello')
def hello_world():
    session['name'] = 'Stone.J'
    return render_template('hello.html', name=session['name'])


if __name__ == '__main__':
    app.run()
