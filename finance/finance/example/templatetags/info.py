#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
Created on 2011-4-19

@author: stone
'''
from django import template
register = template.Library()

def info(context):
    return {'info':'Info.J'}

register.inclusion_tag('example/info.html', takes_context=True)(info)
