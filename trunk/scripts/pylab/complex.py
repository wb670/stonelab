# coding: utf-8
class DictBean(dict):
    '''dynamic bean'''
    
    def __getattr__(self, key):
        return self[key]
    
    def __setattr__(self, key, value):
        self[key] = value
    
    def __delattr__(self, key):
        del self[key]
        

numbers = [1, 3, 5, 7, 8, 25, 4, 20]
print [(i, numbers[i]) for i in numbers if sum(numbers[0:i]) == sum(numbers[i + 1:])]

print max((len(l), l) for l in open('/etc/passwd'))

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