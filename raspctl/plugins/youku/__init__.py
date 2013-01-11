from raspctl import Plugin

urls = (
    '/', 'Index',
    '/youku', 'Youku',
)

plugin = Plugin('youku', urls)


class Index:
    def GET(self):
        return plugin.render.index()

class Youku:
    def GET(self):
        return 'youku'
