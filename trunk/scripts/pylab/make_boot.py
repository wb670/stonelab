#!/usr/bin/env python
# encoding: utf-8
"""
make_boot.py

Created by stone on 2012-11-15.
Copyright (c) 2012 __MyCompanyName__. All rights reserved.
"""

import sys
import os


if len(sys.argv) < 2:
    print '''Usage:
    make_boot bin
    '''
    sys.exit(0)
    
f = open(sys.argv[1], 'r+')
f.seek(510)
f.write(chr(0x55))
f.write(chr(0xAA))
f.close()
print 'Make Done.'
