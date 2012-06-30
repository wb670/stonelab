from flask import Flask, request, redirect, make_response, url_for, render_template

app = Flask(__name__)

@app.route('/index')
def index():
    return 'Hello World.'

if __name__ == '__main__':
    app.run()
