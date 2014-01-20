CROSSDOMAIN='''<?xml version="1.0"?>
<cross-domain-policy>
    <allow-access-from domain="*" to-ports="*" />
</cross-domain-policy>
'''

import web

urls = (
    '/crossdomain.xml', 'CrossDomain'
)

class CrossDomain:
    def GET(self):
        return CROSSDOMAIN

if __name__ == "__main__":
    app = web.application(urls, globals())
    app.run()
