import web
from web.contrib.template import render_jinja


urls = (
    '/', 'Index',        
    '/player', 'Player',
)

render = render_jinja('templates', encoding='utf-8')

class Index:
    def GET(self):
        return render.index()

class Player:
    def GET(self):
        return render.player()

if __name__ == '__main__':
    app = web.application(urls, globals())
    app.run()
