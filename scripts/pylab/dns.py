#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
Created on 2011-5-12

@author: stone
'''
from SocketServer import BaseRequestHandler, ThreadingUDPServer
from StringIO import StringIO
import socket

class Dns:
    def __init__(self, server):
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        self.sock.connect(server)
        
    def query(self, data):
        self.sock.sendall(data)
        return self.sock.recv(65535)
    
    def close(self):
        self.sock.close()

class DnsProxyHandler(BaseRequestHandler):
    def handle(self):
        data, sock = self.request
        dns = self.server.dns
        print self.parser_domain_name(data)
        resp = dns.query(data)
        sock.sendto(resp, self.client_address)
        
    def parser_domain_name(self, data):
        data = StringIO(data)
        data.seek(12)
        list = []
        while True:
            len = ord(data.read(1))
            if len == 0:
                break
            list.append(data.read(len))
        return '.'.join(list)
    
class DnsProxyServer(ThreadingUDPServer):
    def __init__(self, server, dns):
        self.dns = dns
        ThreadingUDPServer.__init__(self, server, DnsProxyHandler)

dns = Dns(('10.20.0.97', 53))
proxy = DnsProxyServer(('127.0.0.1', 53), dns)
proxy.serve_forever()
