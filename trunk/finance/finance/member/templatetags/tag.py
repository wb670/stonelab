#encoding:utf8
from django import template
from finance.fee.models import Account

register = template.Library()

class AccountNode(template.Node):
    def __init__(self, name):
        self.name = name
        
    def render(self, context):
        context[self.name] = Account.objects.get()
        return ''
    
def get_account(parser, token):
    try:
        tag_name, name = token.split_contents()
    except ValueError:        
        raise template.TemplateSyntaxError, "%s tag requires argument" % tag_name
    return AccountNode(name)

register.tag('get_account', get_account)
