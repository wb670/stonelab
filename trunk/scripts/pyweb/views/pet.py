#!/usr/bin/python
# encoding: utf-8

from config import web, session
from models import pet

render = web.template.render('templates/pet', base="../layout", globals={'session': session})

class List:
    def GET(self):
        pets = pet.list()
        return render.list(pets)
    
