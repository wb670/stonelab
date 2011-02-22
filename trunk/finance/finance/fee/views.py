#encoding:utf8
from django import forms
from django.contrib.admin import widgets
from django.core.paginator import Paginator
from django.forms.models import ModelForm
from django.http import HttpResponseRedirect
from django.shortcuts import render_to_response
from finance.fee.models import Account, Bank, Cost, Revenue
from django.template import RequestContext

class BankForm(ModelForm):
    date = forms.DateField(widget=widgets.AdminDateWidget) 
    class Meta:
        model = Bank
        
class CostForm(ModelForm):
    date = forms.DateField(widget=widgets.AdminDateWidget)
    class Meta:
        model = Cost
        
class RevenueForm(ModelForm):
    date = forms.DateField(widget=widgets.AdminDateWidget)
    class Meta:
        model = Revenue

def account_get(req):
    account = Account.objects.get()
    return render_to_response('account/account.html', {'account':account}, context_instance=RequestContext(req))

def bank_add(req):
    if req.method == 'GET':
        form = BankForm()
        return render_to_response('bank/add.html', {'form':form}, context_instance=RequestContext(req))
    else:
        form = BankForm(req.POST)
        if not form.is_valid():
            return render_to_response('bank/add.html', {'form':form}, context_instance=RequestContext(req))
        bank = form.save(False)
        account = Account.objects.get()
        #存款
        if bank.type == 'D':
            if account.cash < bank.amount:
                return render_to_response('bank/add.html', {'form':form, 'error':'账户现金不足'}, context_instance=RequestContext(req))
            account.cash = account.cash - bank.amount
            account.bank = account.bank + bank.amount
        #取款
        else :
            if account.bank < bank.amount:
                return render_to_response('bank/add.html', {'form':form, 'error':'账户存款不足'}, context_instance=RequestContext(req))
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
    return render_to_response('bank/list.html', {'page':page}, context_instance=RequestContext(req))

def cost_add(req):
    if req.method == 'GET':
        form = CostForm()
        return render_to_response('cost/add.html', {'form':form}, context_instance=RequestContext(req))
    else:
        form = CostForm(req.POST)
        if not form.is_valid():
            return render_to_response('cost/add.html', {'form':form}, context_instance=RequestContext(req))
        cost = form.save(False)
        account = Account.objects.get()
        if cost.amount > account.cash:
            return render_to_response('cost/add.html', {'form':form, 'error':'账户现金不足'}, context_instance=RequestContext(req))
        account.cash = account.cash - cost.amount
        cost.save()
        account.save()
        return HttpResponseRedirect('/fee/cost/list')
        
        
def cost_list(req, num=1):
    p = Paginator(Cost.objects.all().order_by('-id'), 10)
    num = int(num)
    num = num if num <= p.num_pages else p.num_pages
    page = p.page(num)
    return render_to_response('cost/list.html', {'page':page}, context_instance=RequestContext(req))

def revenue_add(req):
    if req.method == 'GET':
        form = RevenueForm()
        return render_to_response('revenue/add.html', {'form':form}, context_instance=RequestContext(req))
    else:
        form = RevenueForm(req.POST)
        if not form.is_valid():
            return render_to_response('revenue/add.html', {'form':form}, context_instance=RequestContext(req))
        revenue = form.save(False)
        account = Account.objects.get()
        account.cash = account.cash + revenue.amount
        revenue.save()
        account.save()
        return HttpResponseRedirect('/fee/revenue/list')
        
def revenue_list(req, num=1):
    p = Paginator(Revenue.objects.all().order_by('-id'), 10)
    num = int(num)
    num = num if num <= p.num_pages else p.num_pages
    page = p.page(num)
    return render_to_response('revenue/list.html', {'page':page}, context_instance=RequestContext(req))
