#!/usr/bin/python
# encoding: utf-8

from config import db

def list():
    pets = db.select('pet')
    return pets