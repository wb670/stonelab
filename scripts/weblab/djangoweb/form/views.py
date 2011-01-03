# Create your views here.
from django import forms
from django.shortcuts import render_to_response
from django.http import HttpResponse

class ContactForm(forms.Form):
    subject = forms.CharField()
    email = forms.EmailField(required=False)
    message = forms.CharField(widget=forms.Textarea)

def index(request):
    form = ContactForm()
    return render_to_response('form/index.html', {'form':form})

def post(request):
    form = ContactForm(request.POST)
    if form.is_valid():
        contact = form.cleaned_data
        print contact
        return HttpResponse("OK")
    else:
        return render_to_response('form/index.html', {'form':form})
