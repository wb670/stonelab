#encoding:utf8
from django import forms
from django.contrib.admin import widgets
from django.core.paginator import Paginator
from django.forms.models import ModelForm
from django.http import HttpResponseRedirect
from django.shortcuts import render_to_response
from finance.fee.models import Account, Bank

class BankForm(ModelForm):
    date = forms.DateField(widget=widgets.AdminDateWidget) 
    class Meta:
        model = Bank

def account_get(req):
    account = Account.objects.get()
    return render_to_response('account/account.html', {'account':account})

def bank_add(req):
    if req.method == 'GET':
        form = BankForm()
        return render_to_response('bank/add.html', {'form':form})
    else:
        form = BankForm(req.POST)
        if not form.is_valid():
            return render_to_response('bank/add.html', {'form':form})
        bank = form.save(False)
        account = Account.objects.get()
        #存款
        if bank.type == 'D':
            if account.cash < bank.amount:
                return render_to_response('bank/add.html', {'form':form, 'error':'账户现金不足'})
            account.cash = account.cash - bank.amount
            account.bank = account.bank + bank.amount
        #取款
        else :
            if account.bank < bank.amount:
                return render_to_response('bank/add.html', {'form':form, 'error':'账户存款不足'})
            account.cash = account.cash + bank.amount
            account.bank = account.bank - bank.amount
        account.save()
        bank.save()
        return HttpResponseRedirect('/fee/bank/list')

def bank_list(req, num=1):
    p = Paginator(Bank.objects.all().order_by('-id'), 10)
    num = int(num)
    num = num if num <= p.num_pages else p.num_pages
    page = p.page(num)
    return render_to_response('bank/list.html', {'page':page})