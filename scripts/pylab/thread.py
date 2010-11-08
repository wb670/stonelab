'''
Created on 2010-11-5

@author: stone
'''
import threading
import time

class CountDownLatch(object):
    
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


def worker(latch):
    time.sleep(3)
    latch.count_down()
    
latch = CountDownLatch(10)
for i in xrange(10):
    threading.Thread(target=worker, args=(latch,)).start()

latch.await()
print 'end'    
