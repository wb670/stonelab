'''
Created on Oct 30, 2011
@author: stone
'''
#encoding:utf-8

from web import form
from web.contrib.template import render_jinja
import sys
import web

web.config.debug = True        

urls = (
    '/', 'List',
    '/list', 'List',
    '/add', 'Add',
    '/delete/(\d+)', 'Delete',
    '/update/(\d+)', 'Update',
)

render = render_jinja(
    'templates/gift',
    encoding='utf-8'
)

render._lookup.globals.update(
    web=web
)

db = web.database(dbn='sqlite', db='gift.db')

app = web.application(urls, globals())

gift_form = form.Form(
    form.Textbox('name', form.notnull),
    form.Textarea('detail', form.notnull),
    form.Button('submit', type='submit')
)

class List:
    def GET(self):
        gifts = db.select('gift', what='id,name')
        return render.list({'gifts':gifts})
    
class Add:
    def GET(self):
        return render.add({'form':gift_form})
    def POST(self):
        form = gift_form()
        if not form.validates():
            return render.add({'form':form})
        else:
            db.insert('gift', name=form.name.value, detail=form.detail.value)
            web.seeother('/list')
            
class Delete:
    def GET(self, id):
        db.delete('gift', where='id=$id', vars={'id':id})
        web.seeother('/list')
        
class Update:
    def GET(self, id):
        rs = list(db.select('gift', where='id=$id', vars={'id':int(id)}))
        if rs and len(rs) > 0:
            form = gift_form(rs[0])
            return render.update({'form':form})
    def POST(self, id):
        form = gift_form()
        if not form.validates():
            return render.update({'form':form})
        db.update('gift', where='id=$id', vars={'id':id}, name=form.name.value, detail=form.detail.value)
        web.seeother('/list')
        

if __name__ == "__main__":
    sys.argv.append('127.0.0.1:8888')
    app.run()
