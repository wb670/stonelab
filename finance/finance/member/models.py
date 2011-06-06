#encoding:utf8

from django.db import models

class Member(models.Model):
    types = (('H', u'住宅'),
             ('S', u'商铺'),)
    kinds = (('R', u'出租'),
             ('L', u'自住'),
             ('V', u'空置'))
    rent_kinds = (('O', u'业主'),
                   ('R', u'租户'))
    type = models.CharField(max_length=8, choices=types, verbose_name=u'类型')
    room_no = models.CharField(max_length=64, verbose_name=u'房号')
    name = models.CharField(max_length=64, verbose_name=u'业主姓名')
    area = models.FloatField(verbose_name=u'住房面积')
    fee_unit_price = models.FloatField(verbose_name=u'物业费单价')
    identity_card_no = models.CharField(max_length=32, verbose_name=u'身份证号')
    phone_no = models.CharField(max_length=32, blank=True, verbose_name=u'联系电话')
    kind = models.CharField(max_length=8, choices=kinds, verbose_name=u'用房性质')                                 
    rent_name = models.CharField(max_length=64, verbose_name=u'租户姓名', blank=True)
    rent_contact_no = models.CharField(max_length=64, verbose_name=u'联系方式', blank=True)
    rent_fee_kind = models.CharField(max_length=8, choices=rent_kinds, verbose_name=u'付款方式', blank=True)
    motor_info = models.TextField(max_length=512, blank=True, verbose_name=u'机动车信息')
    nonmotor_info = models.TextField(max_length=512, blank=True, verbose_name=u'非机动车信息')
    memo = models.TextField(max_length=1024, blank=True, verbose_name=u'维修情况/备注')
    
    @classmethod
    def get(cls, id):
        try:
            return cls.objects.get(id=id)
        except:
            return None
        
    def __unicode__(self):
        return '%s' % (self.room_no)