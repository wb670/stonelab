#coding:utf8

from django.db import connection
from django.http import HttpResponseRedirect

class SqlLogMiddleware(object):
    def process_response(self, req, res):
        for sql in connection.queries:  
            print sql
        return res

class Auth(object):
    def process_request(self, req):
        if req.path == '/admin/':
            return
        if not req.user.is_authenticated():
            return HttpResponseRedirect('/admin/')
