#encoding:utf8
from django.db import models
from finance.member.models import Member

class Account(models.Model):
    cash = models.FloatField()
    bank = models.FloatField()

class Bank(models.Model):
    types = (('D', u'存款'),
             ('W', u'取款'))
    type = models.CharField(max_length=8, choices=types, verbose_name=u'类型')
    amount = models.FloatField(verbose_name=u'金额')
    date = models.DateField(verbose_name=u'日期')
    subject = models.CharField(max_length=128, verbose_name=u'摘要')
    
    def add(self):
        account = Account.objects.get()
        #存款
        if self.type == 'D':
            if account.cash < self.amount:
                raise ErrorCode('账户现金不足')
            account.cash = account.cash - self.amount
            account.bank = account.bank + self.amount
        #取款
        else :
            if account.bank < self.amount:
                raise ErrorCode('账户存款不足')
            account.cash = account.cash + self.amount
            account.bank = account.bank - self.amount
        self.save()
        account.save()

class Cost(models.Model):
    codes = (('', u'物业管理成本'),
             ('C00101', u'管理员工资'),
             ('C00102', u'保安工资'),
             ('C00103', u'绿化员工资'),
             ('C00104', u'保洁工资'),
             ('C00105', u'电工工资'),
             ('C00106', u'其他'),
             
             ('', u'物业经营成本'),
             ('C00201', u'水费'),
             ('C00202', u'电费'),
             ('C00203', u'办公用品'),
             ('C00204', u'绿化用品'),
             ('C00205', u'保洁用品'),
             ('C00206', u'工作服'),
             ('C00207', u'保安用品'),
             ('C00208', u'生活垃圾清运费'),
             ('C00209', u'装修垃圾清运费'),
             ('C00210', u'绿化垃圾清运费'),
             ('C00211', u'差旅费'),
             ('C00212', u'餐费'),
             ('C00213', u'通讯费'),
             ('C00214', u'公共用品'),
             ('C00215', u'代缴款'),
             ('C00216', u'其他'),
             
             ('', u'物业大修成本'),
             ('C00301', u'电梯年检费'),
             ('C00302', u'电梯维护费'),
             ('C00303', u'其他公共维修费'))
    code = models.CharField(max_length=16, choices=codes, verbose_name=u'代号')
    amount = models.FloatField(verbose_name=u'金额')
    date = models.DateField(verbose_name=u'日期')
    subject = models.CharField(max_length=128, verbose_name=u'摘要')
    member = models.ForeignKey(Member, null=True, blank=True, verbose_name=u'业主')
    
    def add(self):
        account = Account.objects.get()
        if self.amount > account.cash:
            raise ErrorCode('账户现金不足')
        account.cash = account.cash - self.amount
        self.save()
        account.save()
        
class Revenue(models.Model):
    codes = (('', u'物业费'),
             ('S00101', u'物业费'),
             
             ('', u'停车费'),
             ('S00201', u'地下车库固定停车费'),
             ('S00202', u'地上固定停车费'),
             ('S00203', u'每日临时停车费'),
             ('S00204', u'电瓶车停车费'),
             
             ('', u'装修押金'),
             ('S00301', u'装修押金'),
             ('S00302', u'退款情况'),
             
             ('', u'其他收入'),
             ('S00401', u'电梯运行费及垃圾清运费'),
             ('S00402', u'物业出租'),
             ('S00403', u'维修费'),
             ('S00404', u'代收款'),
             ('S00405', u'其他'))
    code = models.CharField(max_length=16, choices=codes, verbose_name=u'代号')
    amount = models.FloatField(verbose_name=u'金额')
    date = models.DateField(verbose_name=u'日期')
    subject = models.CharField(max_length=128, verbose_name=u'摘要')
    member = models.ForeignKey(Member, null=True, blank=True, verbose_name=u'业主')
    
    def add(self):
        account = Account.objects.get()
        account.cash = account.cash + self.amount
        self.save()
        account.save()
    
class ErrorCode(Exception):
    def __init__(self, msg):
        self.msg = msg
