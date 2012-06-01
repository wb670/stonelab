'''
Created on Oct 9, 2011
@author: stone
'''
import web
import sys

web.config.debug = True        

urls = (
    #'/(.*)', 'hello',
    '/jump', 'Jump',
    '/pass', 'Pass',
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

class Jump:
    def GET(self):
        web.setcookie('PSessionID', '123456789')
        web.redirect('http://pass.abc.net:8888/pass', '301')

class Pass:
    def GET(self):
        print str(web.cookies())
        return 'Pass'

if __name__ == "__main__":
    sys.argv.append('0.0.0.0:8888')
    app.run()
