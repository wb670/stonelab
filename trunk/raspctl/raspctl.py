import web, json, time, os
from web.contrib.template import render_jinja
from rasplib import player, local_file, plugins, cnf, JSONEncoderX, Omxplayer, LocalFile

base_urls = (
    '/', 'Index',     
    '/config', 'Config',
    '/api', 'Api',
    '/player', 'Player',
    '/file', 'File',
)

render = render_jinja('templates', encoding='utf-8')

class Index:
    def GET(self):
        return 'RaspCTL'

class Config:
    def GET(self):
        return render.config()

    @classmethod
    def load(cls):
        data = []
        for i, s in enumerate(cnf.data.sections()):
            data.append({'name':s, 'items':[]})
            for item in cnf.data.items(s):
                data[i]['items'].append(item)
        return data

    @classmethod
    def save(cls, section, key, value):
        cnf.data.set(section, key, value)
        cnf.save()

class Player:
    def GET(self):
        return render.player()

    @classmethod
    def play(cls, index=0, loop=None):
        player.play(int(index), loop=='True')
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
        if names == '' or resources == '':
            player.set_playlist([])
        else:
            names = names.split('#')
            resources = resources.split('#')
            for i in zip(resources, names):
                player.add_playitem(i)
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

class File:
    def GET(self):
        return render.file()

    @classmethod
    def list(cls, dir):
        ndir = '%s%s' % (LocalFile.MEDIA_ROOTPATH, dir)
        l = local_file.list(ndir, LocalFile.MEDIA_FORMATS)
        return [(i[0], i[1], os.path.basename(i[0]))for i in l]
    @classmethod
    def list_all(cls, dir):
        ndir = '%s%s' % (LocalFile.MEDIA_ROOTPATH, dir)
        l = local_file.list_all(ndir, LocalFile.MEDIA_FORMATS, True)
        return [(i[0], i[1], os.path.basename(i[0]))for i in l]

class System:

    @classmethod
    def shutdown(cls):
        os.system('sudo shutdown -h now')
    @classmethod
    def reboot(cls):
        os.system('sudo reboot')

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
    plugins.urls.extend(base_urls)
    plugins.load_all()
    print plugins.urls
    app = web.application(plugins.urls, globals())
    app.run()
