#coding:utf8

from django.db import connection
from django.shortcuts import render_to_response

class SqlLogMiddleware(object):
    def process_response(self, req, res):
        for sql in connection.queries:  
            print sql
        return res

class Auth(object):
    def process_response(self, req, res):
        if res.status_code != 200:
            return res
        if req.path == '/admin/':
            return res
        if not req.user.is_authenticated():
            return render_to_response('admin/login.html', {'app_path':'/admin/'})
        return res