# encoding:utf8
#!/usr/bin/env python
from common import Cursor
import MySQLdb

'''name    | owner | species | sex  | birth      | death'''

con = MySQLdb.connect(host='127.0.0.1', user='sample',
                  passwd='123456', db='sample', cursorclass=Cursor, charset='utf8')

cursor = con.cursor()
sql = "insert into order_item (order_no,pid) values ('%s','%s')"
cursor.close()
con.close()