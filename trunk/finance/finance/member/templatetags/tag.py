#encoding:utf8
from django import template
from finance.fee.models import  CashAccount, BankAccount, Account

register = template.Library()

class AccountNode(template.Node):
    def __init__(self, name):
        self.name = name
        
    def render(self, context):
        cash = CashAccount.objects.get()
        banks = BankAccount.objects.all()
        account = Account(cash.amount, sum([bank.amount for bank in banks]), banks)
        context[self.name] = account
        return ''
    
def get_account(parser, token):
    try:
        tag_name, name = token.split_contents()
    except ValueError:        
        raise template.TemplateSyntaxError, "%s tag requires argument" % tag_name
    return AccountNode(name)

register.tag('get_account', get_account)
