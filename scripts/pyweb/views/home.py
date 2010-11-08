#!/usr/bin/python
# encoding: utf-8

from config import web, session, form

render = web.template.render('templates/home', base="../layout", globals={'session': session})

class Index:
    msg = 'Welcome, PyWeb!'
    msg_form = form.Form(
        form.Textbox('username', form.regexp(r'.{3,8}', '3~8'), description='Username'),
        form.Button('submit', type='submit', description='submit'),
        validators=[form.Validator('Name ERROR', lambda i: i.username != 'ERROR')]
    )

    def GET(self):
        f = self.msg_form()
        return render.index(self.msg, f)
    
    def POST(self):
        f = self.msg_form()
        if not f.validates():
            return render.index(self.msg, f)
        else:
            session.name = f.username.get_value()
            return render.index(self.msg, f)        
