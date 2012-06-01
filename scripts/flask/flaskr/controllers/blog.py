'''
Created on Jun 1, 2012

@author: stone
'''
from flask import Blueprint, request, render_template, redirect, flash, url_for
import sqlite3

blog = Blueprint('blog', 'controllers.blog', template_folder='../templates/blog', url_prefix='/blog');

def connect_db():
    return sqlite3.connect('/tmp/blog.db')

@blog.route('/add', methods=['POST'])
def add_entry():
    db = connect_db()
    db.execute('insert into entries (title, text) values (?,?)',
                     [request.form['title'], request.form['text']])
    db.commit()
    db.close()
    flash('New entry was successfully posted')
    return redirect(url_for('blog.show_entries'))    

@blog.route('/')
def show_entries():
    db = connect_db()
    cur = db.execute('select id,title,text from entries order by id desc')
    entries = [ dict(id=row[0], title=row[1], text=row[2]) for row in cur.fetchall()]
    cur.close()
    db.close()
    return render_template('show_entries.html', entries=entries)
