import threading
import time
import pdbx

def b(name):
    time.sleep(100000)
def a():
    b("test")

def myrun(name):
    threading.Thread(target=a, name=name,).start()

myrun("t1")
myrun("t2")


pdbx.enable_pystack()
for i in xrange(10000000):
    print i
    time.sleep(10)
print 'done'
