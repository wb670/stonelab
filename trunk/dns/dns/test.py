'''
Created on 2011-5-24
@author: stone
'''
from protocol import Header, Query, DnsRequest
import socket
import threading
import time

class CountDownLatch(object):
    '''
    count down latch
    '''    
    def __init__(self, count=1):
        self.count = count
        self.con = threading.Condition()

    def count_down(self):
        self.con.acquire()
        if(self.count > 0):
            self.count = self.count - 1
        self.con.notifyAll()
        self.con.release()

    def await(self):
        self.con.acquire()
        while(self.count > 0):
            self.con.wait()
        self.con.release()


id = 0x5162
flags = 0x0100
ip = '127.0.0.1'
#ip = '10.20.0.97'
#ip = '218.108.248.245'

def test():
    global id, flags
    header = Header(id, flags, 1, 1, 0, 0)
    query = Query('www.google.com', Query.TYPE_A, Query.CLASS_IN)
    req = DnsRequest(header, [query])
    data = req.serialize()
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    sock.connect((ip, 53))
    sock.settimeout(3)
    sock.sendall(data)
    sock.recv(65535)
    sock.close()

def test_all(latch):
    for _ in range(10000):
        try:
            test()
        except Exception as e:
            print e
    latch.count_down()

def performance():
    latch = CountDownLatch(10)
    start = time.time()
    for _ in xrange(10):
        threading.Thread(target=test_all, args=(latch,)).start()
    latch.await()
    print time.time() - start

performance()




