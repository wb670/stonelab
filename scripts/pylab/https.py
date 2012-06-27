'''
Created on Jun 15, 2012

@author: stone
'''
from httplib import HTTPSConnection
con = HTTPSConnection('www.alibaba-inc.com', cert_file='/home/stone/tmp/jinli.pem')
con.request('get', '/xx')
res = con.getresponse()
print res.status
res.close()

con.request('get', '/welcome.nsf/pages/welcome')
res = con.getresponse()
print res.status
res.close()

con.close()
