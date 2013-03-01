from socket import socket, AF_INET, SOCK_STREAM

class SocketX(socket):
    
    def __init__(self, family=2, type=1, proto=0, _sock=None):
        socket.__init__(self, family, type, proto, _sock)
        
    def readline(self):
        return socket.recv(self, 1)
    
s = SocketX(AF_INET, SOCK_STREAM)
s.connect(('localhost', 9999))
s.send('binfo')
print s.readline()
