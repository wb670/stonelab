#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
Created on 2011-5-12

@author: stone
'''
from SocketServer import BaseRequestHandler, ThreadingUDPServer
from StringIO import StringIO
import socket
import time

class Dns:
    def __init__(self, server):
        self.server = server
        
    def query(self, data):
        sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        sock.connect(self.server)
        sock.sendall(data)
        resp = sock.recv(65535)
        sock.close()
        return resp

class DnsProxyHandler(BaseRequestHandler):
    def handle(self):
        data, sock = self.request
        print time.ctime(), self.client_address, self.parser_domain_name(data)
        resp = Dns(self.server.dns_server).query(data)
        sock.sendto(resp, self.client_address)

    def parser_domain_name(self, data):
        data = StringIO(data)
        data.seek(12)
        l = []
        while True:
            ll = ord(data.read(1))
            if len == 0:
                break
            l.append(data.read(ll))
        return '.'.join(l)
    
    def parser_response(self, domain, record):
        pass
    
class DnsProxyServer(ThreadingUDPServer):
    def __init__(self, local_server, dns_server):
        self.local_server = local_server
        self.dns_server = dns_server
        ThreadingUDPServer.__init__(self, local_server, DnsProxyHandler)

proxy = DnsProxyServer(('127.0.0.1', 53), ('10.20.0.97', 53))
proxy.serve_forever()
