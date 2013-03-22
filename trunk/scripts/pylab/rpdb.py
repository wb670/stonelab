'''
Created on 2013-3-21

@author: stone
'''
from pdb import Pdb
from socket import socket, AF_INET, SOCK_STREAM, SOL_SOCKET, SO_REUSEADDR

class Rpdb(Pdb):
    '''Remote PDB Debugger'''

    def __init__(self, port=8787):
        # pdb server
        self.skt = socket(AF_INET, SOCK_STREAM)
        self.skt.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
        self.skt.bind(('0.0.0.0', port))
        self.skt.listen(1)
        # listen for debugger client
        self.client , self.client_addr = self.skt.accept()
        self.client_io = self.client.makefile('rw')
        print 'Rpdb(%s:%s) debugger: starting.' % self.client_addr
        # init pdb
        Pdb.__init__(self, stdin=self.client_io, stdout=self.client_io)

    def do_quit(self, arg):
        print 'Rpdb(%s:%s) debugger: stoped.' % self.client_addr
        self.client.close()
        self.client_io.close()
        self.skt.close()
        return Pdb.do_quit(self, arg)
    do_exit = do_q = do_quit

    def do_EOF(self, arg):
        print 'Rpdb(%s:%s) debugger: stoped.' % self.client_addr
        self.client.close()
        self.client_io.close()
        self.skt.close()
        return Pdb.do_EOF(self, arg)
