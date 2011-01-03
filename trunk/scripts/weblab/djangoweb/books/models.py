#!/usr/bin/python
# -*- coding: utf-8 -*-


from django.db import models

class Publisher(models.Model):
    name = models.CharField(max_length=30)
    address = models.CharField(max_length=50)
    city = models.CharField(max_length=60)
    state_province = models.CharField(max_length=30)
    country = models.CharField(max_length=50)
    website = models.URLField()
    
    def __unicode__(self):
        return self.name
    
    
class Author(models.Model):
    salutation = models.CharField(max_length=10)
    first_name = models.CharField(max_length=30)
    last_name = models.CharField(max_length=40)
    email = models.EmailField()
    headshot = models.ImageField(upload_to='upload')
    
    def __unicode__(self):
        return self.first_name
    
    
class Book(models.Model):
    title = models.CharField(u'标题', max_length=100)
    authors = models.ManyToManyField(Author)
    publisher = models.ForeignKey(Publisher)
    publication_date = models.DateField()
    
    def __unicode__(self):
        return self.title
