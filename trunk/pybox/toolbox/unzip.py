#!/usr/bin/env python
# encoding: utf-8
"""
unzip.py

Created by stone on 2012-09-17.
Copyright (c) 2012. All rights reserved.
"""


from optparse import OptionParser
from zipfile import ZipFile, ZIP_DEFLATED, ZIP_STORED
import sys, os

#command options
parser = OptionParser(usage='%prog [-options] <zipfile> <files>')
parser.add_option('-e', '--encoding', dest='encoding', default=sys.getfilesystemencoding(), help='specify file name\'s encoding')
opts, args = parser.parse_args()
#command options check
if not len(args) == 1:
    parser.print_help()
    sys.exit(-1)

#config vars
name = args[0].decode(sys.stdin.encoding)
encoding = opts.encoding

zf = ZipFile(name)
for i in zf.namelist():
    f = i.decode(encoding)
    if f.endswith('/'): 
        if not os.path.exists(f):
            os.mkdir(f)
    else:
        open(f, 'w').write(zf.read(i))
zf.close()
