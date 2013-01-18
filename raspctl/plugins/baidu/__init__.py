from rasplib import Plugin
from urllib2 import urlopen, Request, quote
import re, json

urls = (
    '/', 'Index',
)

plugin = Plugin('baidu','stone2083', '0.1', urls)


class Index:

    ENCODING    = 'GBK'
    SEARCH_URL  = 'http://video.baidu.com/v?word=%s'
    RES_URL     = 'http://video.baidu.com/htvplaysingles/?id=%s&site=%s'
    HEADERS     = {
        'User-Agent':'Mozilla/5.0 (Windows NT 6.2; rv:16.0) Gecko/20100101 Firefox/16.0'
    }
    PATTERN = re.compile('T.object.extend\((.*), {"alias"')

    def GET(self):
        return plugin.render.index()

    @classmethod
    def search(cls, kw):
        kw = quote(kw.encode(Index.ENCODING))
        info  = urlopen(Request(Index.SEARCH_URL % (kw), headers=Index.HEADERS)).read().decode(Index.ENCODING)
        vl = Index.PATTERN.findall(info)
        return [json.loads(v) for v in vl]

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
