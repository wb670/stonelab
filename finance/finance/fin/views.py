#coding:utf8
from django.shortcuts import render_to_response
from django.forms.models import ModelForm
from finance.fin.models import User

class UserForm(ModelForm):
    class Meta:
        model = User

def user_add(req):
    if req.method == 'GET':
        form = UserForm()
        return render_to_response('user/add.html', {'form':form})
    else:
        form = UserForm(req.POST)
        if not form.is_valid():
            return render_to_response('user/add.html', {'form':form})
        user = form.save(False)
        user.save()
        return render_to_response('user/add.html', {'form':form})

def user_update():
    pass

def user_delete():
    pass

def user_query():
    pass
