from rasplib import Plugin

urls = (
    '/', 'Index',
    '/youku', 'Youku',
)

plugin = Plugin('youku','stone2083', '0.1', urls)


class Index:
    def GET(self):
        return plugin.render.index()

    @classmethod
    def hello(cls):
        return 'hello youku'

class Youku:
    def GET(self):
        return 'youku-NotSupported.'
