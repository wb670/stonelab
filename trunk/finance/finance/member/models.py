#encoding:utf8

from django.db import models

class Member(models.Model):
    kinds = (('R', u'出租'),
             ('L', u'自住'),
             ('V', u'空置'))
    room_no = models.CharField(max_length=64, verbose_name=u'房号')
    name = models.CharField(max_length=64, verbose_name=u'业主姓名')
    area = models.FloatField(verbose_name=u'住房面积')
    fee_unit_price = models.FloatField(verbose_name=u'物业费单价')
    identity_card_no = models.CharField(max_length=32, verbose_name=u'身份证号')
    phone_no = models.CharField(max_length=32, blank=True, verbose_name=u'联系电话')
    kind = models.CharField(max_length=8, choices=kinds, verbose_name=u'用房性质')                                 
    bicycle_garage_no = models.CharField(max_length=16, blank=True, verbose_name=u'自行车库号')
    garage_no = models.CharField(max_length=16, blank=True, verbose_name=u'汽车停车位')
    parking_no = models.CharField(max_length=16, blank=True, verbose_name=u'停车卡号')
    memo = models.TextField(max_length=1024, blank=True, verbose_name=u'备注')
    
    def __unicode__(self):
        return '%s' % (self.room_no)
