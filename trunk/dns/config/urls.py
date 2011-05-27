from django.conf.urls.defaults import patterns

# Uncomment the next two lines to enable the admin:
# from django.contrib import admin
# admin.autodiscover()

urlpatterns = patterns('',
    # Example:
    # (r'^dns/', include('dns.foo.urls')),

    # Uncomment the admin/doc line below and add 'django.contrib.admindocs' 
    # to INSTALLED_APPS to enable admin documentation:
    # (r'^admin/doc/', include('django.contrib.admindocs.urls')),

    # Uncomment the next line to enable the admin:
    # (r'^admin/', include(admin.site.urls)),
    (r'^$', 'host.views.host_list'),
    (r'^index/$', 'host.views.host_list'),
    #host
    (r'^host/list/$', 'host.views.host_list'),
    (r'^host/add/$', 'host.views.host_add'),
    (r'^host/update/([\w-]+)/$', 'host.views.host_update'),
    (r'^host/delete/([\w-]+)/$', 'host.views.host_delete'),
    #ip
    (r'^ip/list/$', 'host.views.ip_list'),
    (r'^ip/info/([\w\.]+)/$', 'host.views.ip_info'),
    (r'^ip/delete/([\w\.]+)/$', 'host.views.ip_delete'),
    (r'^ip/update/([\w-]+)/$', 'host.views.ip_update'),
)
