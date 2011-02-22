#encoding:utf8
from finance.member.models import Member
from django.forms.models import ModelForm
from django.db.models import Q
from django.shortcuts import render_to_response
from django.http import HttpResponseRedirect
from finance import member
from django.core.paginator import Paginator


class MemberForm(ModelForm):
    class Meta:
        model = Member

def add(req):
    if req.method == 'GET':
        form = MemberForm()
        return render_to_response('add.html', {'form':form})
    else:
        form = MemberForm(req.POST)
        if not form.is_valid():
            return render_to_response('member/add.html', {'form':form})
        member = form.save(False)
        member.save()
        return HttpResponseRedirect('/member/%d' % (member.id))
    
def update(req, id):
    if req.method == 'GET':
        try:
            member = Member.objects.get(id=id)
        except Member.DoesNotExist:
            member = None
        form = MemberForm(instance=member)
        return render_to_response('update.html', {'form':form})
    else:
        form = MemberForm(req.POST)
        if not form.is_valid():
            return render_to_response('update.html', {'form':form})
        else:
            member = form.save(False)
            member.id = int(id)
            member.save()
            return HttpResponseRedirect('/member/%d' % (member.id))
    
def get(req, id):
    try:
        member = Member.objects.get(id=id)
    except Member.DoesNotExist:
        member = None
    return render_to_response('member.html', {'member':member})

def delete(req, id):
    Member.objects.filter(id=id).delete()
    return HttpResponseRedirect('/member/list')

def list(req, num=1):
    q = req.GET.get('q')
    if not q:
        p = Paginator(Member.objects.all().order_by('id'), 10)
    else:
        print Q(room_no__contains=q) | Q(name__contains=q)
        p = Paginator(Member.objects.filter((Q(room_no__contains=q) | Q(name__contains=q))), 10)
    num = int(num)
    num = num if num <= p.num_pages else p.num_pages
    page = p.page(num)    
    return render_to_response('list.html', {'page':page, 'q':q})