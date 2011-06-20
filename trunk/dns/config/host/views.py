# Create your views here.
from config.settings import HOST_DIR, FILE_ENCODING, IP_DIR, \
    PROXY_DNS_CMD_SERVERS
from django.http import HttpResponseRedirect
from django.shortcuts import render_to_response
import os
import re
import socket

name_pattern = re.compile('^[0-9a-z-]{1,50}$')

CMD_IP = 'IP load %s'
CMD_HOST = 'HOSTS load %s'

def host_list(req):
    hosts_list = [f for f in os.listdir(HOST_DIR) if not f.startswith(".")]
    hosts_list.sort(key=lambda f: os.path.getmtime(HOST_DIR + f), reverse=True)
    return render_to_response('host/host/list.html', {'list':hosts_list})

def host_add(req):
    if req.method == 'GET':
        return render_to_response('host/host/add.html', {})
    else:
        name = req.POST.get('name', '').strip()
        content = req.POST.get('content', '')
        error = None
        if not name_pattern.match(name):
            error = 'Name Field must be a-z,0-9,and - !'
            return render_to_response('host/host/add.html', {'name':name, 'content':content, 'error':error})
        if os.path.exists(HOST_DIR + name):
            error = 'Hosts named %s is already exists!' % name
            return render_to_response('host/host/add.html', {'name':name, 'content':content, 'error':error})
        open(HOST_DIR + name, 'w').write(content.encode(FILE_ENCODING))
        cmd(CMD_HOST % name)
        return HttpResponseRedirect('/host/list/')
        

def host_update(req, name):
    if req.method == 'GET':
        content = open(HOST_DIR + name).read() if os.path.exists(HOST_DIR + name) else ''
        return render_to_response('host/host/update.html', {'name':name, 'content':content})
    else:
        content = req.POST.get('content')
        open(HOST_DIR + name, 'w').write(content.encode(FILE_ENCODING))
        cmd(CMD_HOST % name)
        return HttpResponseRedirect('/host/list/')

def host_delete(req, name):
    if os.path.exists(HOST_DIR + name):
        os.remove(HOST_DIR + name)
        cmd(CMD_HOST % name)
    return HttpResponseRedirect('/host/list/')

def ip_list(req):
    ip_list = [f for f in os.listdir(IP_DIR) if not f.startswith(".")]
    ip_list.sort(key=lambda f: os.path.getmtime(IP_DIR + f), reverse=True)
    return render_to_response('host/ip/list.html', {'list':ip_list})

def ip_info(req, ip):
    content = open(IP_DIR + ip).read() if os.path.exists(IP_DIR + ip) else ''
    return render_to_response('host/ip/info.html', {'ip':ip, 'content':content})
    
def ip_delete(req, ip):
    if os.path.exists(IP_DIR + ip):
        os.remove(IP_DIR + ip)
    cmd(CMD_IP % ip)
    return HttpResponseRedirect('/ip/list/')

def ip_update(req, name):
    ip = req.META.get('REMOTE_ADDR')
    open(IP_DIR + ip, 'w').write(name)
    cmd(CMD_IP % ip)
    return HttpResponseRedirect('/ip/list/')

def cmd(data):
    for cmd in PROXY_DNS_CMD_SERVERS:
        sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        sock.connect(cmd)
        sock.settimeout(5)
        sock.sendall(data)
        sock.close()
