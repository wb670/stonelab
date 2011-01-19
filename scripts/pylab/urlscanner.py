#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
Created on 2011-1-4

@author: stone
'''
from logging import handlers
from sgmllib import SGMLParser
from urlparse import urlparse
import logging
import re
import urllib

user_agent = "Mozilla/5.0 (X11; U; Linux i686; en-US) AppleWebKit/534.10 (KHTML, like Gecko) Chrome/8.0.552.224 Safari/534.10"
ok_status = set([200, 301, 302])
charsets = ['utf-8', 'gbk']

def get_logger():
    logging.basicConfig()
    fmt = logging.Formatter('%(asctime)s - %(levelname)s: %(message)s')
    hdlr = handlers.RotatingFileHandler('/home/stone/tmp/us.log', 'w')
    hdlr.setFormatter(fmt)
    logger = logging.getLogger()
    logger.addHandler(hdlr)
    logger.setLevel(logging.DEBUG)
    return logger
logger = get_logger()

class Ananlysis(SGMLParser):
    
    def __init__(self, url):
        SGMLParser.__init__(self)
        self.url = re.sub('\r|\n|#.*$', '', url)
        self.suburls = set()
        self.status = None
        self.msg = 'Empty'
        
        try:
            o = urlparse(url)
            self.scheme = o.scheme
            self.host = o.hostname
            self.port = o.port
        except Exception as e:
            self.status = 1000
            self.msg = str(e)
            return
        
        try:
            res = urllib.urlopen(self.url)
            self.status = res.code
                
            if self.status == 200 \
                and 'text/htm' in res.headers['Content-Type']:
                content_type = res.headers['Content-Type']
                content = res.read()
                if 'charset' in content_type:
                    charset = content_type[content_type.index('charset') + 8:]
                    body = content.decode(charset)
                else:
                    for charset in charsets:
                        try:
                            body = content.decode(charset)
                            break
                        except:
                            pass
                        body = ''

                SGMLParser.feed(self, body)
            
        except Exception as e:
            self.status = 1001
            self.msg = str(e)
        
    def start_a(self, attrs):
        for name, value in attrs:
            if name == 'href':
                if value.startswith('http'):
                    pass
                elif value.startswith('/'):
                    value = self.scheme + '://' + self.host + value
                else:
                    value = self.url + value
                self.suburls.add(value)

def scan(urls, depth=1, limit=1):
    if depth > limit:
        return
    for url in urls:
        analysis = Ananlysis(url)
        # log
        if analysis.status >= 1000:
            logger.warn('url=%s;status=%d;msg=%s' % (analysis.url, analysis.status, analysis.msg))
        elif analysis.status not in ok_status :
            logger.error('url=%s;status=%d;msg=%s' % (analysis.url, analysis.status, analysis.msg))
        else:
            logger.info('url=%s;status=%d;msg=%s' % (analysis.url, analysis.status, analysis.msg))
        # 递归分析    
        if analysis.status == 200:
            scan(analysis.suburls, depth + 1, limit)
    
def urlscan(url, limit=1):
    scan([url], 1, limit)
    
urlscan('http://www.blogjava.net/', 2)
