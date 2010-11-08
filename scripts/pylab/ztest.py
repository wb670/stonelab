'''
Created on 2010-11-5

@author: stone
'''
def tuplemethod(n1, n2):
    return (n1, n2, n1 + n2)

x, y, z = tuplemethod(10, 90)
print "the return value is x=%s,y=%s,total=%s" % (x, y, z)


print [x for x in range(20) if x % 2]
print [x for x in range(20) if not x % 2]
print [ "%u*%u=%u" % (x, y, x * y) for x in range(1, 10) for y in range(1, 10)]


import MySQLdb.cursors 
con = MySQLdb.connect(host='127.0.0.1',
                      user='sample',
                      passwd='123456',
                      db='sample',
                      cursorclass=MySQLdb.cursors.DictCursor)
cur = con.cursor()
cur.execute('select * from pet')
pets = cur.fetchall()
for p in pets:
    print p['name']
cur.close()
con.close()    

