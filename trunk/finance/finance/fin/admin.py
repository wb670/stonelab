#coding:utf8
from django.contrib import admin
from finance.fin.models import User, Cost, Revenue, Blank

admin.site.register(User)
admin.site.register(Cost)
admin.site.register(Revenue)
admin.site.register(Blank)
