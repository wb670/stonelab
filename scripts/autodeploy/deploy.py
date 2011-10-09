#!/usr/bin/env python
#encoding:utf-8

import time
import os
import web
from web.contrib.template import render_jinja


urls = (
    '/autodeploy', 'Index',
)
render = render_jinja(
        'templates',
        encoding = 'utf-8'
)

upload = 'repo/'
deploy = 'bin/deploy.sh'
restart = 'bin/tomcatctl.sh'

app = web.application(urls, globals())

class Index:
    def GET(self):
        return render.autodeploy()
    def POST(self):
        data = web.input(file={})
        if not data.file.filename.endswith('.war'):
            return render.autodeploy(message='File Format Illegal.')
        try:
            filename = '%s%s-%s' % (upload, time.strftime('%Y-%m-%d-%H-%M-%S') ,data.file.filename)
            open(filename,'w').write(data.file.file.read())
            os.system('%s %s' % (deploy, filename))
            os.system(restart)
            return render.autodeploy(message='Deploy Success.Wait for 30 seconds.')
        except Exception as e:
            print e
            return render.autodeploy(message='Deploy Fail.')

app = web.application(urls, globals())
if __name__ == '__main__':
    app.run()
