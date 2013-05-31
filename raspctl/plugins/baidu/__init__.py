#encoding:utf-8

from rasplib import Plugin
from urllib2 import urlopen, Request, quote
import re, json
from pyquery import PyQuery

urls = (
    '/', 'Index',
)

plugin = Plugin('baidu','stone2083', '0.1', urls)


class Index:

    ENCODING    = 'GBK'
    SEARCH_URL  = 'http://video.baidu.com/v?word=%s'
    HEADERS     = {
        'User-Agent':'Mozilla/5.0 (Windows NT 6.2; rv:16.0) Gecko/20100101 Firefox/16.0'
    }

    def GET(self):
        kw = u'少年包青天'
        kw = quote(kw.encode(Index.ENCODING))
        info = urlopen(Request(Index.SEARCH_URL % (kw), headers=Index.HEADERS)).read().decode(Index.ENCODING)
        info = info.replace('/browse_static', 'http://video.baidu.com/browse_static')
        pq = PyQuery(info)
        #pq('head>title').remove()
        #pq('div.poster-sec').remove()
        #pq('div#header').remove()
        #pq('div#navbar').remove()
        #pq('div#normalarea').remove()
        header, body = pq('head').html(), pq('body').html()
        return plugin.render.index2(
                header = header, body = body, info=info, pq = pq)


    @classmethod
    def search(cls, kw):
        kw = quote(kw.encode(Index.ENCODING))
        info = urlopen(Request(Index.SEARCH_URL % (kw), headers=Index.HEADERS)).read().decode(Index.ENCODING)
        info = info.replace('/browse_static', 'http://video.baidu.com/browse_static')
        headers = re.findall('<head>([^真]*)</head>', info)[0]
        print headers
        

    @classmethod
    def play(cls, name, url):
        urls = plugin.media_url.get_urls(url)
        if urls == None or len(urls) == 0:
            return None
        playlist = [(u, '%s-%d' % (name, i)) for (i, u) in enumerate(urls)] if len(urls) > 1 else [(u, name) for u in urls]
        plugin.player.set_playlist(playlist)
        return playlist
