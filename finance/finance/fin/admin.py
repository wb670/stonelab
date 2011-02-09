#coding:utf8
from django.contrib import admin
from finance.fin.models import User, Cost, Revenue

class UserAdmin(admin.ModelAdmin):
    list_display = ('room_no', 'name')
    list_per_page = 20
    search_fields = ('room_no', 'name')
    pass

class CostAdmin(admin.ModelAdmin):
    list_display = ('code', 'amount', 'date')
    list_per_page = 20
    list_filter = ('date', 'code')
    pass

class RevenueAdmin(admin.ModelAdmin):
    list_display = ('code', 'amount', 'date', 'user')
    list_per_page = 20
    list_filter = ('date', 'code')
    

admin.site.register(User, UserAdmin)
admin.site.register(Cost, CostAdmin)
admin.site.register(Revenue, RevenueAdmin)
