#encoding:utf8

from django.contrib import admin
from finance.member.models import Member

class MemberAdmin(admin.ModelAdmin):
    list_display = ('room_no', 'name')
    search_fields = ('name',)
    list_per_page = 10

admin.site.register(Member, MemberAdmin)
