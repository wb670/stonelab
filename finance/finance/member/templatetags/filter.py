#encoding:utf8
from django import template

def row(value):
    if not value:
        return 'row1'
    if value % 2 == 1:
        return 'row1'
    else:
        return 'row2'
    
def mul(value, num):
    return value * num

register = template.Library()    
register.filter('row', row)
register.filter('mul', mul)
