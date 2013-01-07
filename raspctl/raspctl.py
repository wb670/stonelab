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
        return 'RaspCTL'

class Player:
    def GET(self):
        return render.player()

class Api:

    def GET(self):
        web.header('Content-Type', 'application/json')
        i = web.input(name=None, data='{}')
        if not i.name:
            return self.result(None,'FAIL','Illegal Arguments', None)
        try:
            data = eval('%s(%s)' % (i.name, self.dict2args(json.loads(i.data)))) 
            return self.result(i.name, 'Success', 'Success', data)
        except Exception as e:
            return self.result(i.name, 'FAIL', str(e), None)

    def result(self, api, status, message, data):
        r = {}
        r['api']=api;r['status']=status;r['message']=message;r['data']=data
        return json.dumps(r, cls=JSONEncoderX)

    def dict2args(self, data):
        return ','.join(['%s=%s' % (k, data[k] if not isinstance(data[k], unicode) else '"%s"' % data[k]) for k in data])


if __name__ == '__main__':
    app = web.application(urls, globals())
    app.run()
