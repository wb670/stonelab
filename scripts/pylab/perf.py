'''
Created on 2010-11-3

@author: stone

bean 35s
var  17s
'''
import time

class Bean(object):
    pass

b = Bean()

s = time.time()
for i in xrange(10000 * 10000):
    id = 1
    name = 'name'
    code = 10
print time.time() - s
