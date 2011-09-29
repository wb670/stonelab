from flask import Flask
from flask.globals import request
from flask.helpers import flash, url_for
from flask.templating import render_template
from werkzeug.utils import redirect
import sqlite3

DATABASE = '/tmp/flaskr.db'
DEBUG = True
SECRET_KEY = 'key'
USERNAME = 'admin'
PASSWORD = 'default'

app = Flask(__name__)
app.config.from_object(__name__)

def connect_db():
    return sqlite3.connect(app.config['DATABASE'])

@app.route('/add', methods=['POST'])
def add_entry():
    db = connect_db()
    db.execute('insert into entries (title, text) values (?,?)',
                     [request.form['title'], request.form['text']])
    db.commit()
    db.close()
    flash('New entry was successfully posted')
    return redirect(url_for('show_entries'))    

@app.route('/')
def show_entries():
    db = connect_db()
    cur = db.execute('select id,title,text from entries order by id desc')
    entries = [ dict(id=row[0], title=row[1], text=row[2]) for row in cur.fetchall()]
    cur.close()
    db.close()
    return render_template('show_entries.html', entries=entries)


if __name__ == '__main__':
    app.run()
