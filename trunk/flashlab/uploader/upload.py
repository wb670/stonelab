CROSSDOMAIN='''<?xml version="1.0"?>
<cross-domain-policy>
    <allow-access-from domain="*" />
</cross-domain-policy>
'''

BASE='/Users/stone/Tmp/ace'

import web

urls = (
    '/crossdomain.xml', 'CrossDomain',
    '/upload',          'Upload',
)

class CrossDomain:
    def GET(self):
        return CROSSDOMAIN

class Upload:
    def GET(self):
        return 'Welcome Here.'

    def POST(self):
        f = web.input(file={})['file']
        open('%s/%s' % (BASE, f.filename), 'wb').write(f.file.read())
        return 'OK'

if __name__ == "__main__":
    app = web.application(urls, globals())
    app.run()
