#!/usr/bin/env python
# encoding: utf-8
"""
fconv.py

Created by stone on 2012-10-09.
Copyright (c) 2012 __MyCompanyName__. All rights reserved.
"""

import sys, os
from optparse import OptionParser

parser = OptionParser()
parser.add_option('-f', '--from-encoding', dest='fencoding', default=sys.getfilesystemencoding(), help='Specifies the encoding of the input')
parser.add_option('-t', '--to-encoding', dest='tencoding', default=sys.getfilesystemencoding(), help='Specifies the encoding of the output')
opts,args = parser.parse_args()
#args valid
if len(args) == 0:
    parser.print_help()
    sys.exit(-1)
if not os.path.isfile(args[0]):
    print 'file named %s is not file.' % args[0]
    sys.exit(-1)

fn = args[0] 
try:
    print 'file named %s start.' % fn
    value=open(fn, 'r').read().decode(opts.fencoding).encode(opts.tencoding)
    open(fn,'w').write(value)
    print 'file named %s stoped.' % fn
except Exception as e:
    print 'file named %s failed. error message is: %s' % (fn, str(e))