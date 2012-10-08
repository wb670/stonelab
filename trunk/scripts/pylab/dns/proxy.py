#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
Created on 2011-5-20

@author: stone
'''
from SocketServer import BaseRequestHandler, ThreadingUDPServer
from cStringIO import StringIO
from protocol import DnsRequest, Answer, DnsResponse, FLAGS_RESPONSE_OK
import socket
import time

DEBUG = True

class Hosts:
    
    def __init__(self, hosts_file):
        self.hosts = []
        list = [line.strip() for line in open(hosts_file) if line.strip() != '' and not line.strip().startswith('#')]
        for l in list:
            info = l.split()
            self.hosts.extend([(h, info[0]) for h in info[1:]])
            
    def get_ip(self, domain):
        for host in self.hosts:
            if host[0].startswith('*'):
                if domain.endswith(host[0][2:]):
                    return host[1]
            else:
                if host[0] == domain:
                    return host[1]
        return None
            
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
        hosts = self.server.hosts
        
        req = DnsRequest.parse(StringIO(data))
        domain = req.queries[0].name
        
        ip = hosts.get_ip(domain) 
        if ip:
            self.log('%s -- [%s] %s %s %s' % (self.client_address[0], time.ctime(), domain, 'Found', ip))
            header = req.header
            header.flags = FLAGS_RESPONSE_OK
            header.answer_rrs = 1
            query = req.queries[0]
            answer = Answer(domain, Answer.TYPE_A, Answer.CLASS_IN, 60, ip)
            resp = DnsResponse(header, [query], [answer]).serialize()
        else:
            self.log('%s -- [%s] %s %s' % (self.client_address[0], time.ctime(), domain, 'Not Found'))
            resp = Dns(self.server.dns_server).query(data)
        sock.sendto(resp, self.client_address)
    
    def log(self, message):
        global DEBUG
        if DEBUG:
            print message

    
class DnsProxyServer(ThreadingUDPServer):
    def __init__(self, local_server, dns_server, hosts='/ets/hosts'):
        self.local_server = local_server
        self.dns_server = dns_server
        self.hosts = Hosts(hosts)
        ThreadingUDPServer.__init__(self, local_server, DnsProxyHandler)

DnsProxyServer(('127.0.0.1', 53), ('10.13.2.1', 53), '/Users/stone/Downloads/hosts').serve_forever()
