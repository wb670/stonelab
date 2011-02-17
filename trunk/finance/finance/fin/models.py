#coding:utf8
from django.db import models

class User(models.Model):
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
    
class Cost(models.Model):
    codes = (('C001', u'物业管理成本'),
             ('C00101', u'管理员工资'),
             ('COO102', u'保安工资'),
             ('COO103', u'绿化员工资'),
             ('COO104', u'保洁工资'),
             ('COO105', u'电工工资'),
             ('COO106', u'其他'),
             ('C002', u'物业经营成本'),
             ('CO0201', u'水费'),
             ('CO0202', u'电费'),
             ('CO0203', u'办公用品'),
             ('CO0204', u'绿化用品'),
             ('CO0205', u'保洁用品'),
             ('CO0206', u'工作服'),
             ('CO0207', u'保安用品'),
             ('CO0208', u'生活垃圾清运费'),
             ('CO0209', u'装修垃圾清运费'),
             ('CO0210', u'绿化垃圾清运费'),
             ('CO0211', u'差旅费'),
             ('CO0212', u'餐费'),
             ('CO0213', u'通讯费'),
             ('CO0214', u'公共用品'),
             ('CO0215', u'代缴款'),
             ('CO0216', u'其他'),
             ('C003', u'物业大修成本'),
             ('CO0301', u'电梯年检费'),
             ('CO0302', u'电梯维护费'),
             ('CO0303', u'其他公共维修费'))
    code = models.CharField(max_length=16, choices=codes, verbose_name=u'费用类型代号')
    subject = models.CharField(max_length=128, verbose_name=u'摘要')
    amount = models.FloatField(verbose_name=u'金额')
    date = models.DateField(verbose_name=u'日期')
    user = models.OneToOneField(User, verbose_name=u'业主')

    def __unicode__(self):
        return '%s : %f' % (self.code, self.amount)
    
    
class Revenue(models.Model):
    codes = (('S001', u'物业费'),
             ('S002', u'停车费'),
             ('S00201', u'地下车库固定停车费'),
             ('S00202', u'地上固定停车费'),
             ('S00203', u'每日临时停车费'),
             ('S003', u'装修押金'),
             ('S004', u'其他收入'),
             ('S00401', u'电梯运行费及垃圾清运费'),
             ('S00402', u'物业出租'),
             ('S00403', u'维修费'),
             ('S00404', u'其他'))
    code = models.CharField(max_length=16, choices=codes, verbose_name=u'费用类型代号')
    subject = models.CharField(max_length=128, verbose_name=u'摘要')
    amount = models.FloatField(verbose_name=u'金额')
    date = models.DateField(verbose_name=u'日期')
    user = models.OneToOneField(User, blank=True, verbose_name=u'业主')
    
    def __unicode__(self):
        return '%s : %f' % (self.code, self.amount)
    
class Blank(models.Model):
    types = (('I', u'存款'),
            ('O', u'取款'))
    type = models.CharField(max_length=8, choices=types, verbose_name=u'类型')
    subject = models.CharField(max_length=128, verbose_name=u'摘要')
    amount = models.FloatField(verbose_name=u'金额')
    date = models.DateField(verbose_name=u'日期')
    
    def __unicode__(self):
        return '%s : %f' % (self.type, self.amount)
