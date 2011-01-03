#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
Created on 2010-11-20

@author: stone
'''

from datetime import datetime
from datetime import timedelta
from django.http import HttpResponse
from django.template import Template
from django.template.context import Context
from django.shortcuts import render_to_response

def current_datetime(request):
    now = datetime.now()
    html = '<html><body>It is now %s.</body></html>' % now
    return HttpResponse(html)

def hours_ahead(request, offset):
    #offset = int(offset)
    now = datetime.now() + timedelta(hours=offset)
    print now
    html = '<html><body>in %s hours,it will be %s.</body></html>' % (offset, now)
    return HttpResponse(html)

def templates(request):
    t = Template('This is {{name}}. Current Time is {{ now|date:"j, Y"}}')
    c = Context({'name':'Stone.J', 'now':datetime.now()})
    html = t.render(c)
    return HttpResponse(html)

def ttime(request):
    return render_to_response('ttime.html', {'now':datetime.now()})