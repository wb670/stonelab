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
    #fee urls
    #fee acoount 
    (r'^fee/account/account/$', 'fee.views.account_get'),
    #fee bank
    (r'^fee/bank/add/$', 'fee.views.bank_add'),
    (r'^fee/bank/list/$', 'fee.views.bank_list'),
    (r'^fee/bank/list/(\d+)/$', 'fee.views.bank_list'),
)
