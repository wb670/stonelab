from django.conf.urls.defaults import patterns, include
from django.contrib import admin

admin.autodiscover()

urlpatterns = patterns('',
    (r'^admin/', include(admin.site.urls)),
    #index url
    (r'^$', 'member.views.index'),
    (r'^index/$', 'member.views.index'),
    #member urls
    (r'^member/add/$', 'member.views.add'),
    (r'^member/update/(\d+)/$', 'member.views.update'),
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
    #fee cost
    (r'^fee/cost/add/$', 'fee.views.cost_add'),
    (r'^fee/cost/list/$', 'fee.views.cost_list'),
    (r'^fee/cost/list/(\d+)/$', 'fee.views.cost_list'),
    #fee revenue
    (r'^fee/revenue/add/$', 'fee.views.revenue_add'),
    (r'^fee/revenue/list/$', 'fee.views.revenue_list'),
    (r'^fee/revenue/list/(\d+)/$', 'fee.views.revenue_list'),
    #report
    (r'^report/report/$', 'report.views.report'),
)
