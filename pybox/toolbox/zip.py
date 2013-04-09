#!/usr/bin/env python
# encoding: utf-8
"""
zip.py

Created by stone on 2012-09-17.
Copyright (c) 2012. All rights reserved.
"""


from optparse import OptionParser
from zipfile import ZipFile, ZIP_DEFLATED, ZIP_STORED
import os
import sys

#command options
parser = OptionParser(usage='%prog [-options] <zipfile> <files>')
parser.add_option('-e', '--encoding', dest='encoding', default=sys.getfilesystemencoding(), help='specify file name\'s encoding')
opts, args = parser.parse_args()
#command options check
if len(args) < 2:
    parser.print_help()
    sys.exit(-1)

#config vars
in_files = [f.decode(sys.stdin.encoding) for f in args[1:]]
out_file = args[0]
encoding = opts.encoding
try:
    import zlib
    reload(zlib)
    compress_type = ZIP_DEFLATED
except:
    compress_type = ZIP_STORED

#in_file check
for in_file in in_files:
    if not os.path.exists(in_file):
        print 'warning: files:%s not found.' % (in_file)
        sys.exit(-1)

#compress: file walkup
def walkup(f, zf):
    f = os.path.normpath(f)
    #dir
    if os.path.isdir(f):
        arcname = os.path.normpath(f.encode(encoding)) + '/'
        zf.write(f, arcname)
        print 'updating: %s/ (stored 0%%)' % (f)
        for c in os.listdir(f):
            walkup('%s/%s' % (f, c), zf)
    #file
    else:
        arcname = os.path.normpath(f.encode(encoding))
        zf.write(f, arcname)
        info = zf.getinfo(arcname)
        ctype = 'deflated' if (info.file_size > info.compress_size) else 'stored'
        crate = (info.file_size - info.compress_size) * 100 / info.file_size if(info.file_size > info.compress_size) else 0
        print '  adding: %s (%s %d%%)' % (f, ctype, crate)

zf = ZipFile(out_file, 'w', compress_type)
for in_file in in_files:
    walkup(in_file, zf)
zf.close()
