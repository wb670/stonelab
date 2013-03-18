#!/usr/bin/python
#encoding:utf-8

import sys, os, urllib2, re
from optparse import OptionParser

SIZE = 20

U_LIST = 'http://music.baidu.com/search/tag/%s?start=%d'
U_DOWNLOAD = 'http://music.baidu.com/song/%s/download'

P_LIST_ID = '<li data-songitem = \'{&quot;songItem&quot;:{&quot;sid&quot;:(\d+)}}\''
P_LIST_TITLE = '<a href=".*" class="" data-songdata=\'{ "id": "" }\' title=".*?">(.*?)</a>'
P_LIST_AUTHOR = '<span class="author_list" title="(.*?)">'
P_LIST_TOTAL = 'pageNavigator:{ \'total\':(.*?), \'size\':20, \'start\':(.*?),' 
P_DOWNLOAD = '/data/music/file\?link=(.*) "'
P_FILENAME = 'filename="(.*)"'
P_HTML = '<.*?>'


def list(key, page=0):
    if page <= 0:
        return (0, zip([],[],[]))
    start = (page - 1) * SIZE
    url = U_LIST % (urllib2.quote(key.encode('utf-8')), start)
    r = urllib2.urlopen(url)
    if not r.getcode() == 200:
        return []
    val = r.read().decode('utf-8')
    ids = re.findall(P_LIST_ID, val)
    names = re.findall(P_LIST_TITLE, val)
    authors = re.findall(P_LIST_AUTHOR, val)
    total, start = re.findall(P_LIST_TOTAL, val)[0]
    r.close()
    return (int(total), int(start) / SIZE + 1, zip(ids, authors, [re.sub(P_HTML, '', n)for n in names]))


def download(id, base):
    url = U_DOWNLOAD % (id)
    r = urllib2.urlopen(url)
    if not r.getcode() == 200:
        return
    val = r.read().decode('utf-8')
    res = re.findall(P_DOWNLOAD, val)
    r.close()
    # no download res
    if len(res) == 0:
        return
    #download
    r = urllib2.urlopen(res[0])
    f = re.findall(P_FILENAME, r.headers['Content-Disposition'].decode('gbk'))[0]
    print 'Downloading >>> %s' % f
    open(os.path.normpath(base) + '/' + f, 'wb').write(r.read())
    r.close()


parser = OptionParser()
parser.add_option('-m', '--mode', dest='mode', default='list', help='specifies the command mode [list|download|multidownload]')
parser.add_option('-o', '--output', dest='output', default='./', help='specifies the output dir for download')
parser.add_option('-p', '--page', dest='page', default='1', help='specifies the list page')
parser.add_option('-k', '--key', dest='key', default=None, help='specifies the mp3 keyworld')
parser.add_option('-f', '--from', dest='frompage', default=None, help='specifies the from page for multidownload')
parser.add_option('-t', '--to', dest='topage', default=None, help='specifies the end page for multidownload')
opts,args = parser.parse_args()

if not opts.mode in ['list', 'download', 'multidownload']:
    parser.print_help()

#list
enc = sys.getfilesystemencoding()
if opts.mode == 'list':
    if not opts.key:
        print 'Usage: dmp3.py -m list -key key -p page'
        sys.exit(-1)
    total, page, items = list(opts.key.decode(enc), int(opts.page))
    print 'Total: %d Page:%d' % (total, page)
    for item in items:
        print '%s%s\t%s' % (item[0].ljust(10), item[1].ljust(20), item[2])
elif opts.mode == 'download':
    if not os.path.isdir(opts.output):
        print 'Usage: dmp3.py -m download -o dir -k id'
        sys.exit(-1)
    if not opts.key:
        print 'Usage: dmp3.py -m download -o dir -k id'
        sys.exit(-1)
    download(opts.key, opts.output.decode(enc))
elif opts.mode == 'multidownload':
    if not os.path.isdir(opts.output):
        print 'Usage: dmp3.py -m multidownload -o dir -k key -M max'
        sys.exit(-1)
    if not opts.key:
        print 'Usage: dmp3.py -m multidownload -o dir -k key -M max'
        sys.exit(-1)
    if not opts.key:
        print 'Usage: dmp3.py -m multidownload -o dir -k key -M max'
        sys.exit(-1)
    f = int(opts.frompage) if opts.frompage else 1
    while(True):
        print 'MultiDownloading Page %d' % (f)
        total, page, items = list(opts.key.decode(enc), f)
        t = int(opts.topage) if opts.topage else page
        for item in items:
            download(item[0], opts.output.decode(enc))
        if f >= t:
            break
        f = f + 1
    

    



