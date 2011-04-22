#encoding:utf8
from django.http import HttpResponse
import xlwt
from django.shortcuts import render_to_response
from django import forms
from django.contrib.admin import widgets
from django.template.context import RequestContext
from finance.fee.models import  Cost, Revenue

RevenueCodes = {}
for (k, v) in Revenue.codes:
    RevenueCodes[k] = v
CostCodes = {}
for (k, v) in Cost.codes:
    CostCodes[k] = v

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
        cs = Cost.objects.filter(date__range=(start, end)).order_by('id')
        rs = Revenue.objects.filter(date__range=(start, end)).order_by('id')
        
        wb = xlwt.Workbook()
        _create_property_sheet(wb.add_sheet(u'物业费'), rs)
        _create_parking_sheet(wb.add_sheet(u'停车费'), rs)
        _create_decoration_sheet(wb.add_sheet(u'装修押金'), rs)
        _create_other_sheet(wb.add_sheet(u'其他收入'), rs)
        _create_costdetail_sheet(wb.add_sheet(u'支出明细'), cs)
        return xls_to_response(wb, u'download.xls')


def _create_property_sheet(st, rs):
    codes = ['S00101']
    _create_revenue_sheet(st, rs, codes)
        
def _create_parking_sheet(st, rs):
    codes = ['S00%d' % i for i in range(201, 205)]
    _create_revenue_sheet(st, rs, codes)
        
def _create_decoration_sheet(st, rs):
    codes = ['S00%d' % i for i in range(301, 303)]
    _create_revenue_sheet(st, rs, codes)

def _create_other_sheet(st, rs):
    codes = ['S00%d' % i for i in range(401, 406)]
    _create_revenue_sheet(st, rs, codes)
    
def _create_costdetail_sheet(st, cs):
    codes = ['C00%d' % i for i in range(101, 107)]
    codes += ['C00%d' % i for i in range(201, 217)]
    codes += ['C00%d' % i for i in range(301, 304)]
    _create_cost_sheet(st, cs, codes)

def _create_revenue_sheet(st, rs, codes):
    rows = [row for row in rs if row.code in codes]
    for (i, h) in enumerate([u'年', u'月', u'日', u'摘要'] + [RevenueCodes[k] for k in codes]):
        st.write(0, i, h)
    for (i, row) in enumerate(rows):
        for (j, h) in enumerate([row.date.year, row.date.month, row.date.day, row.subject] + _get_info(row, codes)):
            st.write(i + 1, j, h)
    for (i, h) in enumerate([u'', u'', u'', u'合计'] + [_get_sum(rows, code) for code in codes]):
        st.write(len(rows) + 1, i, h)
        
def _create_cost_sheet(st, cs, codes):
    rows = [row for row in cs if row.code in codes]
    for (i, h) in enumerate([u'年', u'月', u'日', u'摘要'] + [CostCodes[k] for k in codes]):
        st.write(0, i, h)
    for (i, row) in enumerate(rows):
        for (j, h) in enumerate([row.date.year, row.date.month, row.date.day, row.subject] + _get_info(row, codes)):
            st.write(i + 1, j, h)
    for (i, h) in enumerate([u'', u'', u'', u'合计'] + [_get_sum(rows, code) for code in codes]):
        st.write(len(rows) + 1, i, h)

def _get_info(row, codes):
    return [ row.amount if row.code == c else '' for c in codes]

def _get_sum(rows, code):
    return sum([row.amount for row in rows if row.code == code])
    
        
def xls_to_response(xls, fname):
    res = HttpResponse(mimetype="application/ms-excel")
    res['Content-Disposition'] = 'attachment; filename=%s' % fname
    xls.save(res)
    return res
