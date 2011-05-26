'''
Created on 2011-5-26
@author: stone
'''
from SocketServer import BaseRequestHandler, ThreadingUDPServer

#------------------------#
# Message Struct (type, event, body)
# Cmd Result     (result, body)
#------------------------#

class CmdHandler(BaseRequestHandler):

    def handle(self):
        hosts = self.server.hosts
        data, sock = self.request
        
        msg = data.split()
        if(len(msg) != 3):
            sock.sendto('True', self.client_address)
        else:
            type, event , body = msg
            if type == 'IP':
                if event == 'load':
                    hosts.load_ip(body)
                    sock.sendto('True', self.client_address)
                    return
                if event == 'info':
                    h = hosts.repository_ip.get(body)
                    r = str(h) if h else ''
                    sock.sendto(r, self.client_address)
                    return
            if type == 'HOSTS':
                if event == 'load':
                    hosts.load_hosts(body)
                    sock.sendto('True', self.client_address)
                    return
                if event == 'info':
                    d = hosts.hosts.get(body)
                    r = str(d) if d else ''
                    sock.sendto(r, self.client_address)
                    return
            sock.sendto('False', self.client_address)

class CmdServer(ThreadingUDPServer):
    def __init__(self, server, hosts):
        self.hosts = hosts
        ThreadingUDPServer.__init__(self, server, CmdHandler)

