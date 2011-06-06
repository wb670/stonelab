#encoding:utf8
from finance.member.models import Member
from django.forms.models import ModelForm
from django.db.models import Q
from django.shortcuts import render_to_response
from django.http import HttpResponseRedirect
from finance import member
from django.core.paginator import Paginator
from finance.fee.models import Revenue
from django.template import RequestContext


class MemberForm(ModelForm):
    class Meta:
        model = Member

def index(req):
    return render_to_response('index.html', context_instance=RequestContext(req))

def add(req):
    if req.method == 'GET':
        form = MemberForm()
        return render_to_response('member/add.html', {'form':form}, context_instance=RequestContext(req))
    else:
        form = MemberForm(req.POST)
        if not form.is_valid():
            return render_to_response('member/add.html', {'form':form}, context_instance=RequestContext(req))
        member = form.save(False)
        member.save()
        return HttpResponseRedirect('/member/%d/' % (member.id))
    
def update(req, id):
    if req.method == 'GET':
        member = Member.get(id)
        form = MemberForm(instance=member)
        return render_to_response('member/update.html', {'form':form}, context_instance=RequestContext(req))
    else:
        form = MemberForm(req.POST)
        if not form.is_valid():
            return render_to_response('member/update.html', {'form':form}, context_instance=RequestContext(req))
        else:
            member = form.save(False)
            member.id = int(id)
            member.save()
            return HttpResponseRedirect('/member/%d/' % (member.id))
    
def get(req, id):
    try:
        member = Member.objects.get(id=id)
        rs = Revenue.objects.filter(member=member.id).order_by('-id')[0:10]
        r = Revenue.objects.filter(member=member.id).filter(code='S00101').order_by('-id')
        if r:
            r = r[0]
    except Member.DoesNotExist:
        member = None
        rs = None
    return render_to_response('member/member.html', {'member':member, 'r': r, 'rs':rs}, context_instance=RequestContext(req))

def list(req, num=1):
    q = req.GET.get('q')
    if not q:
        p = Paginator(Member.objects.all().order_by('id'), 10)
    else:
        f = Q(room_no__contains=q) | Q(name__contains=q) | Q(motor_info__contains=q) | Q(nonmotor_info__contains=q)
        p = Paginator(Member.objects.filter(f), 10)
    num = int(num)
    num = num if num <= p.num_pages else p.num_pages
    page = p.page(num)    
    return render_to_response('member/list.html', {'page':page, 'q':q}, context_instance=RequestContext(req))
