#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
Created on 2010-11-8

@author: stone
'''
from config import web

class Param:
    def GET(self):
        param = web.input()
        return param
    
class Cookie:
    def GET(self):
        param = web.input(action='')
        if(param['action'] == 'p'):
            web.setcookie('cn', 'Stone.J')
        cn = web.cookies().get('cn')
        return cn
