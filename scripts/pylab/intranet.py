#!/usr/bin/env python
# encoding: utf-8
"""
intranet.py

Created by stone on 2012-08-27.
Copyright (c) 2012 __MyCompanyName__. All rights reserved.
"""

'''
https://ark.taobao.org:4430/arkserver/Login.aspx?app=http%3a%2f%2fis.taobao.org%2fxiaoyouju&redirectURL=http%3a%2f%2fis.taobao.org%2fxiaoyouju%2fEmployee.aspx%3fpostid%3d11
https://ark.taobao.org:4430/arkserver/Login.aspx?app=http%3A%2F%2Fis.taobao.org%2Fxiaoyouju&redirectURL=http%3A%2F%2Fis.taobao.org%2Fxiaoyouju%2FEmployee.aspx%3Fpostid%3D11
'''


import sys
import os
import urllib
from urllib import quote, unquote
from httplib import HTTPSConnection

CERT_FILE = u'/Users/stone/Tmp/.Tmp/liwang.pem'
LOGIN_HOST = u'ark.taobao.org'
LOGIN_PORT = 4430
LOGIN_URL = u'/arkserver/Login.aspx?app=%s&redirectURL=%s'

PORTAL_HOST = u'is.taobao.org'

DEFAULT_HEADERS = {
    'Accept':'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
    'Accept-Charset':'UTF-8,*;q=0.5',
    'Accept-Language':'zh-CN,zh;q=0.8',
    'Connection':'keep-alive',
    'User-Agent':'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_1) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.57 Safari/537.1'
}

def login(app, redirect):
    url = LOGIN_URL % (quote(app, ''), quote(redirect, ''))
    con = HTTPSConnection(LOGIN_HOST, LOGIN_PORT, cert_file = CERT_FILE)
    con.request('GET', url, headers = DEFAULT_HEADERS)
    res = con.getresponse()
    location = res.getheader('Location')
    con.close()
    return location

def postoffice(url):
    con = HTTPSConnection(PORTAL_HOST, cert_file = CERT_FILE)
    con.request('GET', url, headers = DEFAULT_HEADERS)
    res = con.getresponse()
    print res.read()
    con.close()

def main():
    url = login('http://is.taobao.org/xiaoyouju', 'http://is.taobao.org/xiaoyouju/Employee.aspx?postid=11')
    postoffice(url)


if __name__ == '__main__':
	main()
