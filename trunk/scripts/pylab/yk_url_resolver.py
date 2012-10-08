'''
Created on Mar 12, 2012

@author: stone
'''
import json
import math
import urllib2

class UrlResolver(object):
    
    INFO = 'http://v.youku.com/player/getPlayList/VideoIDS/%s'
    SOURCE = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ/\:._-1234567890'
    RESOURCE = 'http://f.youku.com/player/getFlvPath/sid/00_00/st/%s/fileid/%s?K=%s,k2:%s'

    def __init__(self, url):
        self.url = url
        self.data = self._init_data()

    def get_title(self):
		return self.data['title']

    def get_formats(self):
        return self.data['streamfileids'].keys()

    def get_urls(self, fmt):
        if not fmt in self.get_formats():
            return []
        return [UrlResolver.RESOURCE % (fmt, self._get_fileid(fmt, i), seg['k'], seg['k2']) 
                                            for (i, seg) in enumerate(self.data['segs'][fmt])]
        
    
    def _init_data(self):
        vid = self.url[self.url.find('id_') + 3:self.url.find('.html')]
        return json.loads(urllib2.urlopen(UrlResolver.INFO % vid).read())['data'][0]
    
    def _get_fileid(self, fmt, parts):
        file_id = [self._mix_seed(self.data['seed'])[int(l)] for l in self.data['streamfileids'][fmt].split('*') if l]
        parts = '%02X' % parts
        file_id[8], file_id[9] = parts[0], parts[1]
        return ''.join(file_id)
    
    def _mix_seed(self, seed):
        src = list(UrlResolver.SOURCE)
        mixed = []
        seed = float(seed)
        for _ in range(len(src)):
            seed = (seed * 211 + 30031) % 65536
            index = math.floor(seed / 65536 * len(src))
            mixed.append(src[int(index)])
            src.remove(src[int(index)])
        return ''.join(mixed)
        

if __name__ == '__main__':
    r = UrlResolver('http://v.youku.com/v_show/id_XMzYzODA4MTQ4.html')
    print r.get_title()
    print r.get_formats()
    print len(r.get_urls('flv')), r.get_urls('flv')
