#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
Created on 2010-11-28

@author: stone
'''
from Tkconstants import END
import Image
import Tkinter
import os
import tkFileDialog
import tkMessageBox
import re

class ImgCompression(object):
    
    IMG_FORMAT = ('png', 'jpg', 'jpeg', 'bmp')

    def is_img(self, img):
        return img[img.rfind('.') + 1:] in self.IMG_FORMAT 
    
    def compress(self, src, target, quality=50):
        '''compress the image
           src: the src image
           target: the compressed image
           quality: compression quality (0 -- 100)
        '''
        if self.is_img(src):
            Image.open(src).save(target, quality=quality)
    
    
    def multi_compress(self, imgs, targetdir, quality=50):
        if not os.path.exists(targetdir):
            os.makedirs(targetdir)
        for img in imgs:
            name = img[img.rfind('/') + 1:img.rfind('.')]
            self.compress(img, '%s/%s.jpg' % (targetdir, name), quality)

class ImgCompressionFrame(object):
    
    IMG_FORMAT = ('png', 'jpg', 'jpeg', 'bmp')
    
    def __init__(self):
        self.ic = ImgCompression()
        #主桌面
        self.top = Tkinter.Tk()
        self.top.title('图片压缩工具')
        self.top.iconbitmap('')
        #原图片文件选择界面
        self.src_lable = Tkinter.Label(self.top, text='要压缩的图片')
        self.src_lable.grid(row=0, column=0)
        self.src_entry = Tkinter.Entry(self.top, width=60)
        self.src_entry.grid(row=0, column=1)
        self.src_option = Tkinter.Button(self.top, text='选择', command=self.select_src)
        self.src_option.grid(row=0, column=2)
        #输出文件选择界面
        self.target_lable = Tkinter.Label(self.top, text='输出文件路径')
        self.target_lable.grid(row=1, column=0)  
        self.target_entry = Tkinter.Entry(self.top, width=60)
        self.target_entry.grid(row=1, column=1)
        self.target_option = Tkinter.Button(self.top, text='选择', command=self.select_target)
        self.target_option.grid(row=1, column=2)
        #图片压缩质量
        self.quality_lab = Tkinter.Label(self.top, text='压缩质量（0-100）')
        self.quality_lab.grid(row=2, column=0)
        self.quality_entry = Tkinter.Entry(self.top, width=60)
        self.quality_entry.insert(0, 50)
        self.quality_entry.grid(row=2, column=1)
        #压缩
        self.execute = Tkinter.Button(self.top, text='执行', command=self.execute)
        self.execute.grid(row=2, column=2)
        
        Tkinter.mainloop()
        
    def select_src(self):
        path = tkFileDialog.askopenfilenames(title='选择要压缩的文件', filetypes=[('图片文件', ('.png', '.jpg', 'jpeg', '.bmp'))])
        if path:
            self.src_entry.delete(0, END)
            self.src_entry.insert(0, path)
            
    def select_target(self):
        dir = tkFileDialog.askdirectory(title='选择输出目录路径')
        if dir:
            self.target_entry.delete(0, END)
            self.target_entry.insert(0, dir)
    
    def execute(self):
        imgs = self.src_entry.get()
        target = self.target_entry.get()
        quality = self.quality_entry.get()
        #param validate
        if not imgs \
            or  (not os.path.exists(target)) \
            or (not re.match('\d{1,3}', quality)) \
            or (int(quality) < 0 or int(quality) > 100):
                tkMessageBox.showerror('ERROR', '请选择正确的路径和压缩质量')
        else:
            self.ic.multi_compress(imgs.split(), target, int(quality))
            tkMessageBox.showinfo('OK', '压缩成功')
        
frame = ImgCompressionFrame()        

