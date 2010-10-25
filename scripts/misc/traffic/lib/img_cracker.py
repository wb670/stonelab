#!/usr/bin/python
# -*- coding: utf-8 -*-

import os,sys,Image

class TrafficImgCracker(object):
    """
        交通信息网站图片破解
    """
   
    def __init__(self,base):
        """
           base：参考图片基本路径
           codes：图片code和feature的元组集合
        """
        self.base = base
        self.codes = []

        for i in [images for images in os.listdir(base) if images.endswith('bmp')]:
            self.codes.append((i[0],list(Image.open(base+i).getdata())))


    def crack(self,imgfile):
        img = Image.open(imgfile)
        ret = []
        for i in range(1,5):
            i = img.crop((i*9,0,i*9+7,22))
            feature = [f if(f==40) else 15 for f in i.getdata()]
            ret.append(self.__getcode(feature))
        return "".join(ret)


    def __getcode(self,feature):
        for code in self.codes:
            ref = zip(code[1],feature)
            if(len([x for x in ref if x[0]==x[1]]) * 100.0 / len(ref) > 95):
                return code[0]
        return None 
                    



cracker = TrafficImgCracker('conf/imgref/')

if __name__ == "__main__":
    if len(sys.argv) !=2:
        sys.stderr.write("args error.")
        sys.exit()
    sys.stdout.write(cracker.crack(sys.argv[1]))

