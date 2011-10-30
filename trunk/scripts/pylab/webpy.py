'''
Created on Oct 9, 2011
@author: stone
'''
import web
import sys

web.config.debug = True        

urls = (
    '/(.*)', 'hello'
)

def log(handler):
    print 'log.1'
    result = handler()
    print 'log.2'
    return result

def perm(handler):
    print 'perm.1'
    result = handler()
    print 'perm.2'
    return result

app = web.application(urls, globals())
session = web.session.Session(app, web.session.DiskStore('sessions'))

#app.add_processor(log)
#app.add_processor(perm)

class hello:        
    def GET(self, name):
        if not name: 
            name = 'World'
        if name == 'session':
            session.name = 'Stone.S'
        if name == 'cookie':
            web.setcookie('name', 'Stone.C')
        print session.get('name')
        print web.cookies().get('name')
        return 'Hello, ' + name + '!'

if __name__ == "__main__":
    sys.argv.append('127.0.0.1:8888')
    app.run()
