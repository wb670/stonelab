#encoding:utf8

from django.contrib import admin
from finance.fee.models import Account, Bank, Cost, Revenue

admin.site.register(Account)
admin.site.register(Bank)
admin.site.register(Cost)
admin.site.register(Revenue)