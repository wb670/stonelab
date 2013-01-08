import web, json, time
from web.contrib.template import render_jinja
from media import player, local_file, JSONEncoderX, Omxplayer


urls = (
    '/', 'Index',     
    '/api', 'Api',
    '/player', 'Player',
)

render = render_jinja('templates', encoding='utf-8')

class Index:
    def GET(self):
        return render.index()
        #return 'RaspCTL'

class Player:
    def GET(self):
        return render.player()

    @classmethod
    def play(cls, index=0, loop=None):
        player.play(index, loop)
        return Player.get_info()
    @classmethod
    def pause(cls):
        player.pause()
        return Player.get_info()
    @classmethod
    def resume(cls):
        player.resume()
        return Player.get_info()
    @classmethod
    def prev(cls):
        player.prev()
        return Player.get_info()
    @classmethod
    def next(cls):
        player.next()
        return Player.get_info()
    @classmethod
    def lseek(cls):
        player.lseek()
        return Player.get_info()
    @classmethod
    def rseek(cls):
        player.rseek()
        return Player.get_info()
    @classmethod
    def stop(cls):
        player.stop()
        return Player.get_info()
    @classmethod
    def set_dev(cls, dev):
        player.set_dev(dev)
        return Player.get_info()
    @classmethod
    def set_loop(cls, loop):
        player.set_loop(loop=='True')
        return Player.get_info()
    @classmethod
    def get_info(cls):
        return player.get_info()
    @classmethod
    def set_playlist(cls, names, resources):
        player.set_playlist(zip(resources, names))
        return Player.get_info()
    @classmethod
    def add_playitem(cls, name, resource):
        player.add_playitem((resource, name))
        return Player.get_info()
    @classmethod
    def del_playitem(cls, index):
        player.del_playitem(int(index))
        return Player.get_info()
    @classmethod
    def sort_playitem(cls, index, back):
        index, back = int(index), back == 'True'
        player.sort_playitem(index, 1, back)
        return Player.get_info()
    @classmethod
    def mock(cls):
        Omxplayer.CMD = '/Users/stone/Tmp/cmd.sh %s %s'
        info  = Player.get_info()
        info['CMD'] = Omxplayer.CMD
        return info
    @classmethod
    def test(cls):
        music = local_file.list(local_file.AUDIO_ROOTPATH, local_file.AUDIO_FORMATS, True)
        player.set_playlist(zip(music, music))
        return Player.get_info()

class Api:

    def GET(self):
        web.header('Content-Type', 'application/json')
        i = web.input(data='{}')
        api = None
        try:
            api = json.loads(i.data)
            if not api and not hasattr(api,'name'):
                return self.result(api, 'FAIL','Illegal Arguments', None)
            if 'args' not in api: api['args'] = ''
            resp = eval('%s(%s)' % (api['name'], ','.join(['"%s"' % arg for arg in api['args']])))
            return self.result(api, 'Success', 'Success', resp)
        except Exception as e:
            return self.result(api, 'FAIL', str(e), None)

    def result(self, api, status, message, result):
        r = {}
        r['api']=api;r['status']=status;r['message']=message;r['result']=result
        return json.dumps(r, cls=JSONEncoderX)


if __name__ == '__main__':
    app = web.application(urls, globals())
    app.run()
