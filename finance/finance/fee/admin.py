#encoding:utf8

from django.contrib import admin
from finance.fee.models import CashAccount, BankDetail, Cost, Revenue, \
    BankAccount

admin.site.register(CashAccount)
admin.site.register(BankAccount)
admin.site.register(BankDetail)
admin.site.register(Cost)
admin.site.register(Revenue)
