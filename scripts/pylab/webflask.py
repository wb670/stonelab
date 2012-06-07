'''
Created on Jun 7, 2012

@author: stone
'''
from flask import Flask, request, redirect, make_response, url_for, render_template

app = Flask(__name__)

@app.route('/index')
def index():
    return render_template('index.htm')

@app.route('/login_frame')
def login_frame():
    return render_template('login_frame.htm')

@app.route('/cross')
def cross():
    name = request.cookies.get('name')
    pwd = request.cookies.get('pwd')
    return render_template('cross.htm', name=name, pwd=pwd)


@app.route('/login')
def tblogin():
    name = request.args.get('name', '')
    pwd = request.args.get('pwd', '')
    target = request.args.get('target', None)
    
    resp = make_response(redirect(target if target else url_for('index')))
    resp.set_cookie('name', name, domain='.taobao.com')
    resp.set_cookie('pwd', pwd, domain='.taobao.com')
    return resp

@app.route('/jump')
def jump():
    target = request.args.get('target')
    name = request.cookies.get('name')
    pwd = request.cookies.get('pwd')
    url = '%s&sync=%s:%s' % (target, name, pwd)
    return redirect(url)

@app.route('/pass')
def alipass():
    target = request.args.get('target')
    sync = request.args.get('sync')
    print target, sync
    name, pwd = sync.split(':')[0], sync.split(':')[1]
    resp = make_response(redirect(target))
    resp.set_cookie('name', name, domain='.alibaba.com')
    resp.set_cookie('pwd', pwd, domain='.alibaba.com')
    return resp

app.run(host='0.0.0.0', debug=True)
