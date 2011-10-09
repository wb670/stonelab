'''
Created on Oct 9, 2011
@author: stone
'''
import web
        
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

app.add_processor(log)
app.add_processor(perm)

class hello:        
    def GET(self, name):
        if not name: 
            name = 'World'
        return 'Hello, ' + name + '!'

if __name__ == "__main__":
    app.run()
