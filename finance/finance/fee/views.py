#encoding:utf8
from django import forms
from django.contrib.admin import widgets
from django.core.paginator import Paginator
from django.forms.models import ModelForm
from django.http import HttpResponseRedirect
from django.shortcuts import render_to_response
from finance.fee.models import Account, Bank, Cost, Revenue, ErrorCode
from django.template import RequestContext
from finance.member.models import Member

class BankForm(ModelForm):
    date = forms.DateField(widget=widgets.AdminDateWidget, label=u'日期') 
    class Meta:
        model = Bank
        
class CostForm(ModelForm):
    date = forms.DateField(widget=widgets.AdminDateWidget)
    class Meta:
        model = Cost
        exclude = ('member',)
        
class RevenueForm(ModelForm):
    date = forms.DateField(widget=widgets.AdminDateWidget)
    class Meta:
        model = Revenue
        exclude = ('member',)

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
        try:
            bank.add()
        except ErrorCode as code:
            return render_to_response('bank/add.html', {'form':form, 'error':code.msg}, context_instance=RequestContext(req))
        return HttpResponseRedirect('/fee/bank/list/')

def bank_list(req, num=1):
    p = Paginator(Bank.objects.all().order_by('-id'), 10)
    num = int(num)
    num = num if num <= p.num_pages else p.num_pages
    page = p.page(num)
    return render_to_response('bank/list.html', {'page':page}, context_instance=RequestContext(req))

def cost_add(req):
    if req.method == 'GET':
        form = CostForm()
        mid = req.GET.get('mid') 
        member = Member.get(mid) if mid else None
        return render_to_response('cost/add.html', {'form':form,
                                                    'member':member
                                    }, context_instance=RequestContext(req))
    else:
        mid = req.GET.get('mid') 
        member = Member.get(mid) if mid else None
        form = CostForm(req.POST)
        if not form.is_valid():
            return render_to_response('cost/add.html', {'form':form,
                                                        'member':member}, context_instance=RequestContext(req))
        cost = form.save(False)
        if mid:
            cost.member_id = mid
        try:
            cost.add()
        except ErrorCode as code:
            return render_to_response('cost/add.html', {'form':form, 'error': code.msg}, context_instance=RequestContext(req))
        return HttpResponseRedirect('/fee/cost/list/')
        
        
def cost_list(req, num=1):
    p = Paginator(Cost.objects.all().order_by('-id'), 10)
    num = int(num)
    num = num if num <= p.num_pages else p.num_pages
    page = p.page(num)
    return render_to_response('cost/list.html', {'page':page}, context_instance=RequestContext(req))

def revenue_add(req):
    if req.method == 'GET':
        form = RevenueForm()
        mid = req.GET.get('mid') 
        member = Member.get(mid) if mid else None
        return render_to_response('revenue/add.html', {'form':form,
                                                       'member':member,
                                  }, context_instance=RequestContext(req))
    else:
        mid = req.GET.get('mid')
        form = RevenueForm(req.POST)
        member = Member.get(mid) if mid else None
        if not form.is_valid():
            return render_to_response('revenue/add.html', {'form':form,
                                                           'member':member}, context_instance=RequestContext(req))
        revenue = form.save(False)
        if mid:
            revenue.member_id = mid
        revenue.add()
        return HttpResponseRedirect('/fee/revenue/list/')
        
def revenue_list(req, num=1):
    p = Paginator(Revenue.objects.all().order_by('-id'), 10)
    num = int(num)
    num = num if num <= p.num_pages else p.num_pages
    page = p.page(num)
    return render_to_response('revenue/list.html', {'page':page}, context_instance=RequestContext(req))
