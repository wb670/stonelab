#!/usr/bin/python
# coding:utf-8 

'''
Created on 2013-3-22

@author: stone
'''

from rpdb import Rpdb
import random
import time

def add(i, j):
    r = i + j
    return r

def main():
    rpdb = Rpdb()               #启动Remote PDB Debugger，使用默认端口：8787，启动时不强制进入断点（有Debugger客户端连接时断点才生效）
    # rpdb = Rpdb(8787)         #启动Remote PDB Debugger，使用指定端口：8787，启动时不强制进入断点（有Debugger客户端连接时断点才生效）
    # rpdb = Rpdb(startup=True) #启动Remote PDB Debugger，使用默认端口：8787，启动时强制进入断点
    # rpdb = Rpdb(8787, True)   #启动Remote PDB Debugger，使用指定端口：8787，启动时强制进入断点
    while True:
        rpdb.set_trace()
        i = random.randint(1, 10)
        j = random.randint(1, 10)
        r = add(i, j)
        print r
        time.sleep(1)

if __name__ == '__main__':
    main()
