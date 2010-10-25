#!/usr/bin/python
# encoding: utf-8

from config import *

render = web.template.render('templates/home',base="../layout",globals={'session': session})

msg_form = form.Form(
    form.Textbox('username',form.regexp(r'.{3,8}','3~8'),description='Username'),
    form.Button('submit',type='submit',description='submit')
)

msg = 'Welcome, PyWeb!'    

class Index:

    def GET(self):
        f = msg_form()
        return render.index(msg,f) 
    
    def POST(self):
        f = msg_form()
        if not f.validates():
            return render.index(msg,f)
        else:
            session.name = f.username.get_value()
            return render.index(msg,f)
