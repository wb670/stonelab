#!/usr/bin/python
# coding:utf-8
'''
Created on 2010-11-5
@author: stone
garbled detector
'''
import sys, re

regex = (u'\u0000-\u007f',
         u'\u4e00-\u9fa5', u'\u3000-\u303F', u'\uff00-\uffef',
         u'\u2010-\u2030')
special = (u'\u00b7', u'\u03c6', u'\u00d7', u'\u2500', u'\u00b1',
           u'\ufe4e', u'\u00b0', u'\u03a6', u'\u00b0', u'\u00b0', u'\u03b2', u'\u255f', u'\u0424', u'\u02cb', u'\u25bc',
           u'\u0438', u'\u043d', u'\u0432', u'\u0435', u'\u0440', u'\u0442', u'\u043e', u'\u0440', u'\u2161', u'\u2605',
           )

pattern = re.compile(r'^[%s]+$' % ''.join(regex + special))
default_encoding = 'utf-8'

def detect(word):
    if not word:
        return True
    if pattern.match(word):
        return True
    return False

f = sys.argv[1] if len(sys.argv) > 1 else None
encoding = sys.argv[2] if len(sys.argv) > 2 else default_encoding

if not f:
    print u'''Usage:
    gd.py $file $encoding'''
    sys.exit()
for l, text in enumerate(open(f)):
    word = text.decode(encoding)
    if not detect(word):
        print l, word.encode(encoding),
