from django.conf.urls.defaults import patterns, include
from djangoweb import views
from django.contrib import admin
from djangoweb.form import views as formviews

# Uncomment the next two lines to enable the admin:
admin.autodiscover()

urlpatterns = patterns('',
    # Example:
    # (r'^djangoweb/', include('djangoweb.foo.urls')),

    # Uncomment the admin/doc line below and add 'django.contrib.admindocs' 
    # to INSTALLED_APPS to enable admin documentation:
    # (r'^admin/doc/', include('django.contrib.admindocs.urls')),

    # Uncomment the next line to enable the admin:
    (r'^admin/', include(admin.site.urls)),
    (r'^time/$', views.current_datetime),
    (r'^time/plus/(\d+)/$', views.hours_ahead),
    (r'^ttime/$', views.ttime),
    (r'^templates/$', views.templates),
    
    
    (r'form/index/$', formviews.index),
    (r'form/post/$', formviews.post),
)
