#encoding:utf-8

from rasplib import Plugin
from urllib2 import urlopen, Request, quote
import re, json

urls = (
    '/', 'Index',
)

plugin = Plugin('baidu','stone2083', '0.2', urls)


class Index:

    ENCODING    = 'utf-8'
    SEARCH_URL  = 'http://app.video.baidu.com/app?ct=905969664&word=%s&ie=utf-8&version=4.4.1'
    RES_URL     = 'http://app.video.baidu.com/xqsingle/?worktype=adnativetvplay&id=%s&site=%s&version=4.4.1'
    HEADERS     = {
        'User-Agent':'Mozilla/5.0 (Windows NT 6.2; rv:16.0) Gecko/20100101 Firefox/16.0'
    }

    def GET(self):
        return plugin.render.index()


    @classmethod
    def search(cls, kw):
        kw = quote(kw.encode(Index.ENCODING))
        info  = urlopen(Request(Index.SEARCH_URL % (kw), headers=Index.HEADERS)).read().decode(Index.ENCODING)
        return json.loads(info) 

    @classmethod
    def res(cls, id, site):
        return json.loads(
                urlopen(Request(Index.RES_URL % (id, site), headers=Index.HEADERS)).read().decode(Index.ENCODING))


    @classmethod
    def play(cls, name, url):
        urls = plugin.media_url.get_urls(url)
        if urls == None or len(urls) == 0:
            return None
        playlist = [(u, '%s-%d' % (name, i)) for (i, u) in enumerate(urls)] if len(urls) > 1 else [(u, name) for u in urls]
        plugin.player.set_playlist(playlist)
        return playlist
