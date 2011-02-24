#encoding:utf8
from django.http import HttpResponse
import xlwt
from django.shortcuts import render_to_response
from django import forms
from django.contrib.admin import widgets
from django.template.context import RequestContext
from finance.fee.models import Account, Cost, Revenue

class ReportForm(forms.Form):
    start = forms.DateField(widget=widgets.AdminDateWidget, label=u'开始日期')
    end = forms.DateField(widget=widgets.AdminDateWidget, label=u'结束日期') 

def report(req):
    if req.method == 'GET':
        form = ReportForm()
        return render_to_response('report/report.html', {'form':form}, context_instance=RequestContext(req))
    else:
        form = ReportForm(req.POST)
        if not form.is_valid():
            return render_to_response('report/report.html', {'form':form}, context_instance=RequestContext(req))
        start = form.cleaned_data['start']
        end = form.cleaned_data['end']
        account = Account.objects.get()
        cs = Cost.objects.filter(date__range=(start, end)).order_by('code', '-id')
        rs = Revenue.objects.filter(date__range=(start, end)).order_by('code', '-id')
        
        wb = xlwt.Workbook()
        _create_account_sheet(wb.add_sheet(u'账户信息'), start, end, account, cs, rs)
        _create_cost_sheet(wb.add_sheet(u'成本信息'), start, end, account, cs, rs)    
        _create_revenue_sheet(wb.add_sheet(u'收入信息'), start, end, account, cs, rs)
        return xls_to_response(wb, u'download.xls')


def _create_account_sheet(st, start, end, account, cs, rs):
    st.write_merge(0, 0, 0, 2, u'当前账户信息')
    for (i, h) in enumerate([u'现金', u'存款', u'总金额']):
        st.write(1, i, h) 
    for (i, info) in enumerate([account.cash, account.bank, account.cash + account.bank]):
        st.write(2, i, info)
    st.merge(3, 3, 0, 2)
    st.write_merge(4, 4, 0, 2, u'统计情况信息')
    for (i, h) in enumerate([u'统计阶段', u'成本总额', u'收入总额']):
        st.write(5, i, h)
    for (i, info) in enumerate([str(start) + '~' + str(end), sum(c.amount for c in cs), sum(r.amount for r in rs)]):
        st.write(6, i, info)

def _create_cost_sheet(st, start, end, account, cs, rs):
    st.write_merge(0, 0, 0, 3, u'成本明细统计表（%s ~ %s）' % (str(start), str(end)))
    for (i, h) in enumerate([u'日期', u'类型', u'金额', u'摘要']):
        st.write(1, i, h)
    for (i, c) in enumerate(cs):
        for (j, info) in enumerate([str(c.date), c.get_code_display(), c.amount, c.subject]):
            st.write(i + 2, j, info)

def _create_revenue_sheet(st, start, end, account, cs, rs):
    st.write_merge(0, 0, 0, 3, u'收入明细统计表（%s ~ %s）' % (str(start), str(end)))
    for (i, h) in enumerate([u'日期', u'类型', u'金额', u'摘要']):
        st.write(1, i, h)
    for (i, r) in enumerate(rs):
        for (j, info) in enumerate([str(r.date), r.get_code_display(), r.amount, r.subject]):
            st.write(i + 2, j, info)

def xls_to_response(xls, fname):
    res = HttpResponse(mimetype="application/ms-excel")
    res['Content-Disposition'] = 'attachment; filename=%s' % fname
    xls.save(res)
    return res
