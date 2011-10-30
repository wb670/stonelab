from SocketServer import BaseRequestHandler, ThreadingTCPServer
import time
import sys
import __main__


class TimeHandler(BaseRequestHandler):
    
    HTML = '''HTTP/1.1 200 OK
Content-Type: text/plain
Transfer-Encoding: chunked

e
Hello, World!!
0
'''
    
    def handle(self):
        reload(__main__)
        self.request.send(TimeHandler.HTML + '\r\n')
        
s = ThreadingTCPServer(('127.0.0.1', 8888), TimeHandler)
s.serve_forever()
