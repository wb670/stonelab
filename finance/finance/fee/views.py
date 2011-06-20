#encoding:utf8
from django import forms
from django.contrib.admin import widgets
from django.core.paginator import Paginator
from django.db.models import Q
from django.forms.models import ModelForm
from django.forms.widgets import HiddenInput
from django.http import HttpResponseRedirect
from django.shortcuts import render_to_response
from django.template import RequestContext
from finance.fee.models import CashAccount, BankDetail, Cost, Revenue, ErrorCode, \
    BankAccount, Account
from finance.member.models import Member

class BankAccountForm(ModelForm):
    class Meta:
        model = BankAccount
        exclude = ('amount',)

class BankDetailForm(ModelForm):
    date = forms.DateField(widget=widgets.AdminDateWidget, label=u'日期')
    class Meta:
        model = BankDetail
        
class CostForm(ModelForm):
    code = forms.ChoiceField(choices=((k, v if k == '' else '---%s' % v) for (k, v) in Cost.codes), label=u'代号')
    date = forms.DateField(widget=widgets.AdminDateWidget, label=u'日期')
    class Meta:
        model = Cost
        exclude = ('member',)
       
class RevenueForm(ModelForm):
    choices = []
    for (code, value) in Revenue.codes:
        if code == '':
            choices.append((code, value))
        elif code == 'S00302':
            choices.append((code, u'---%s(填写负数)' % value))
        else:
            choices.append((code, u'---%s' % value))
    code = forms.ChoiceField(choices=tuple(choices), label=u'代号')
    date = forms.DateField(widget=widgets.AdminDateWidget, label=u'日期')
    class Meta:
        model = Revenue
        exclude = ('member',)

rcodes = [(k, v) for (k, v) in Revenue.codes if k]
rcodes.insert(0, ('', '------'))
class RSearchForm(forms.Form):
    code = forms.ChoiceField(choices=rcodes, label=u'')
    start = forms.DateField(widget=widgets.AdminDateWidget, label=u'开始日期', required=False)
    end = forms.DateField(widget=widgets.AdminDateWidget, label=u'结束日期', required=False)
    page = forms.IntegerField(widget=HiddenInput, initial=1)

ccodes = [(k, v) for (k, v) in Cost.codes if k]
ccodes.insert(0, ('', '------'))
class CSearchForm(forms.Form):
    code = forms.ChoiceField(choices=ccodes, label=u'')
    start = forms.DateField(widget=widgets.AdminDateWidget, label=u'开始日期', required=False)
    end = forms.DateField(widget=widgets.AdminDateWidget, label=u'结束日期', required=False)
    page = forms.IntegerField(widget=HiddenInput, initial=1)


def account_get(req):
    cash = CashAccount.objects.get()
    banks = BankAccount.objects.all()
    account = Account(cash.amount, sum([bank.amount for bank in banks]), banks)
    return render_to_response('account/account.html', {'account':account}, context_instance=RequestContext(req))

def bank_account_add(req):
    if req.method == 'GET':
        form = BankAccountForm()
        return render_to_response('bc/add.html', {'form': form}, context_instance=RequestContext(req))
    else:
        form = BankAccountForm(req.POST)
        if not form.is_valid():
            return render_to_response('bc/add.html', {'form': form}, context_instance=RequestContext(req))
        bc = form.save(False)
        bc.amount = 0
        bc.save()
        return HttpResponseRedirect('/fee/account/account/')
        
def bank_add(req):
    if req.method == 'GET':
        form = BankDetailForm()
        return render_to_response('bank/add.html', {'form':form}, context_instance=RequestContext(req))
    else:
        form = BankDetailForm(req.POST)
        if not form.is_valid():
            return render_to_response('bank/add.html', {'form':form}, context_instance=RequestContext(req))
        bank = form.save(False)
        try:
            bank.add()
        except ErrorCode as code:
            return render_to_response('bank/add.html', {'form':form, 'error':code.msg}, context_instance=RequestContext(req))
        return HttpResponseRedirect('/fee/bank/list/')

def bank_list(req, num=1):
    p = Paginator(BankDetail.objects.all().order_by('-id'), 10)
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
    if req.method == 'GET':
        form = CSearchForm()
        p = Paginator(Cost.objects.all().order_by('-id'), 10)
        num = 1
    else:
        form = CSearchForm(req.POST)
        qc = form.data['code']
        qs = form.data['start']
        qe = form.data['end']
        qp = int(form.data['page'])
        f = Q(code=qc)
        if qs:
            f = f & Q(date__gte=qs)
        if qe:
            f = f & Q(date__lte=qe)
        p = Paginator(Cost.objects.filter(f).order_by('-id'), 10)
        num = qp
    num = num if num <= p.num_pages else p.num_pages
    page = p.page(num)
    return render_to_response('cost/list.html', {'form':form, 'page':page}, context_instance=RequestContext(req))
    

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
    if req.method == 'GET':
        form = RSearchForm()
        p = Paginator(Revenue.objects.all().order_by('-id'), 10)
        num = 1
    else:
        form = RSearchForm(req.POST)
        qc = form.data['code']
        qs = form.data['start']
        qe = form.data['end']
        qp = int(form.data['page'])
        f = Q(code=qc)
        if qs:
            f = f & Q(date__gte=qs)
        if qe:
            f = f & Q(date__lte=qe)
        p = Paginator(Revenue.objects.filter(f).order_by('-id'), 10)
        num = qp
    num = num if num <= p.num_pages else p.num_pages
    page = p.page(num)
    return render_to_response('revenue/list.html', {'form':form, 'page':page}, context_instance=RequestContext(req))
