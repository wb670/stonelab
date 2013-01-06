import web, json
from web.contrib.template import render_jinja
from media import player, local_file, JSONEncoderX


urls = (
    '/', 'Index',     
    '/api', 'Api',
    '/player', 'Player',
)

render = render_jinja('templates', encoding='utf-8')

class Index:
    def GET(self):
        return render.index()

class Player:
    def GET(self):
        return render.player()

class Api:

    def GET(self):
        web.header('Content-Type', 'application/json')
        i = web.input(name=None, data='')
        if not i.name:
            return self.result(None,'FAIL','Illegal Arguments', None)
        try:
            print '%s(%s)' % (i.name, ','.join(i.data))
            data = eval('%s(%s)' % (i.name, ','.join(i.data))) 
            return self.result(i.name, 'Success', 'Success', r)
        except Exception as e:
            return self.result(i.name, 'FAIL', str(e), None)

    def result(self, api, status, message, data):
        r = {}
        r['api']=api;r['status']=status;r['message']=message;r['data']=data
        return json.dumps(r, cls=JSONEncoderX)


if __name__ == '__main__':
    app = web.application(urls, globals())
    app.run()
