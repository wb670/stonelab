# encoding:utf8
#!/usr/bin/env python

import re
word = u'[[["Mirror","镜像","","Jìngxiàng"]],,"zh-CN",,[["Mirror",[5],1,,676,0,1,0]],[["镜像",5,[["Mirror",676,1,],["Image",101,1,],["Mirroring",51,1,],["Mirrored",2,1,],["Images",0,1,]],[[0,2]],"镜像"]],,,,10,[]]'
p = re.compile(r',([,\]])')
while (p.search(word)):
    word = p.sub(lambda m:',null' + m.group(1), word)
print word