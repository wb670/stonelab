'''
Created on Jun 1, 2012

@author: stone
'''

from flask import render_template, Blueprint

home = Blueprint('home', 'controllers.home', template_folder='../templates/home');

@home.route('/')
@home.route('/index')
def index():
    return render_template('index.html', name='Stone.J')


