# Create your views here.
from django.shortcuts import render_to_response
from django.template.context import RequestContext

def index(req):
    return render_to_response('example/index.html', context_instance=RequestContext(req))
    
