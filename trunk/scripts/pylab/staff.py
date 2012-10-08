#!/usr/bin/python
#encoding:utf-8
import urllib
import json
import sys

DEFAULT_ENCODE = 'UTF-8'
DEFAULT_RES_URL = 'http://buc.vip.xyi.cn.alidc.net/rpc/'

def kv(d, k):
    return d.get(k, '')

def request(url):
    f = urllib.urlopen(url)
    return json.loads(f.read().decode(DEFAULT_ENCODE)) if f.getcode() == 200 else None

def info(users):
    if not users or not users.get('content'):
        return
    for u in users['content']['items']:
        print '%s %s %s %s %s %s %s %s %s ' % (kv(u, 'empId'), kv(u, 'lastName'), kv(u, 'emailAddr'), kv(u, 'extensionPhone'), kv(u, 'cellphone'), kv(u, 'aliWW'), kv(u, 'tbWW'), kv(u, 'depDesc'), kv(u, 'jobDesc'))
    print 'Total Count: %d' % (users['content']['count'])

def find(q, page=1, page_size=50):
    url = DEFAULT_RES_URL + 'userQuery/findUsers/byFuzzyQuery.json?fuzzyStr=%s&page=%d&sizePerPage=%d&available=T&rightFuzzy=false' % (q, page, page_size)
    return request(url)

def main():
    if len(sys.argv) <= 1:
        print 'command fail.'
    elif len(sys.argv) == 2:
        info(find(sys.argv[1]))
    else:
        info(find(sys.argv[1], int(sys.argv[2])))

if __name__ == '__main__':
    main()
    
