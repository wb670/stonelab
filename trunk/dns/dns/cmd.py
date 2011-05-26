'''
Created on 2011-5-26
@author: stone
'''
from SocketServer import BaseRequestHandler, ThreadingUDPServer
from settings import SYS_PASSWORD
from settings import DEBUG
import time

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
            self.log('%s -- [%s] %s' % (self.client_address[0], time.ctime(), data))
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
                    d = hosts.repository_hosts.get(body)
                    r = str(d) if d else ''
                    sock.sendto(r, self.client_address)
                    return
            if type == 'SYS':
                if event == 'close':
                    if body == SYS_PASSWORD: 
                        sock.sendto('True', self.client_address)
                        self.server.proxy_dns.shutdown()
                        self.server.shutdown()
                        return
                    else:
                        sock.sendto('False', self.client_address)
                        return
            sock.sendto('False', self.client_address)
            
    def log(self, message):
        if DEBUG:
            print message

class CmdServer(ThreadingUDPServer):
    def __init__(self, server, hosts, proxy_dns):
        self.hosts = hosts
        self.proxy_dns = proxy_dns
        ThreadingUDPServer.__init__(self, server, CmdHandler)
