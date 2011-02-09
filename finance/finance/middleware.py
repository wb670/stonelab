#coding:utf8

from django.db import connection  

class SqlLogMiddleware(object):
    def process_response(self, request , response):
        for sql in connection.queries:  
            print sql
        print 
        return response
        
