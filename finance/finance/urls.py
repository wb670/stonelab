from django.conf.urls.defaults import patterns, include
from django.contrib import admin

admin.autodiscover()

urlpatterns = patterns('',
    (r'^admin/', include(admin.site.urls)),
    #member urls
    (r'^member/add/$', 'member.views.add'),
    (r'^member/update/(\d+)/$', 'member.views.update'),
    (r'^member/delete/(\d+)/$', 'member.views.delete'),
    (r'^member/(\d+)/$', 'member.views.get'),
    (r'^member/list/$', 'member.views.list'),
    (r'^member/list/(\d+)/$', 'member.views.list'),
)
