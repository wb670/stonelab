# Create your views here.
from config.settings import BASE_HOSTS_DIR
from django.shortcuts import render_to_response
import os

def index(req):
    print '=============='
    return render_to_response('index.html')

def host_update(req, name):
    try:
        content = open('%s%s' % (BASE_HOSTS_DIR, name)).read()
        return render_to_response('host/host/update.html', {'name':name,
                                                          'content':content})
    except:
        return render_to_response('host/host/update.html', {'name':name,
                                                          'error':'Not Found!'})
                                                      

def host_list(req):
    hosts_list = [f for f in os.listdir(BASE_HOSTS_DIR) if not f.startswith(".")]
    return render_to_response('host/host/list.html', {'list':hosts_list})
