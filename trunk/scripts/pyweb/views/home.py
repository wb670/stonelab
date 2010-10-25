#!/usr/bin/python
# encoding: utf-8

from config import *

render = web.template.render('templates/home',base="../layout",globals={'session': session})

class Index:
    def GET(self):
        msg = 'Welcome PyWeb.'
        session.name = 'Stone.J'
        return render.index(msg) 
