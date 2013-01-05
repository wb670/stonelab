#!/usr/bin/env python
# encoding: utf-8
"""
lib.py

Created by stone on 2012-12-27.
Copyright (c) 2012 __MyCompanyName__. All rights reserved.
"""

from threading import Thread
import os
import pexpect

class LocalFile:
    AUDIO_ROOTPATH = '/Users/stone/Downloads'
    VIDEO_ROOTPATH = '/Users/stone/Movies'
    
    AUDIO_FORMATS = ('mp3',)
    VIDEO_FORMATS = ('mkv', 'mp4',)
    
    def __init__(self, encoding='UTF-8'):
        self.encoding = encoding
    
    def list(self, dir, formats):
        if not os.path.isdir(dir):
            return None
        return [media.decode(self.encoding) for media in os.listdir(dir) if media[media.rindex('.') + 1:] in formats] 
    

class Omxplayer:
    '''omxplayer cmd'''
    CMD = 'omxplayer -p -o %s %s' 

    '''omxplayer controllers'''
    CTL_QUIT        = 'q'
    CTL_PASUE       = 'p'
    CTL_RESUME      = 'p'


    '''Omxplayer States'''
    State_Init      = 0
    State_Play      = 1 
    State_Pause     = 2
    
    DEV_HDMI        = 'hdmi'
    DEV_LOCAL       = 'local'

    def __init__(self):
        self.playlist   = []
        self.state      = Omxplayer.State_Init
        self.dev        = Omxplayer.DEV_HDMI
        self.proccess   = None
    
    def play(self, loop=False):
        Thread(target=self.play, args=(loop,)).start()
    
    def _play(self, loop=False):
        if not self.state == Omxplayer.State_Init:
            return
        #play 
        self.state = Omxplayer.State_Play
        while(True):
            for media in self.playlist:
                if self.state == Omxplayer.State_Play:
                    self.proccess = pexpect.spawn(Omxplayer.CMD % (self.dev, media)) 
            if not loop:
                break
        self.state = Omxplayer.State_Init
    
    def pasuse(self):
        if self.state == Omxplayer.State_Play:
            if self.proccess and self.proccess.isavlive():
                self.proccess.send(CTL_PASUE)
                self.state = Omxplayer.State_Pause
        
    def resume(self):
        if self.state == Omxplayer.State_Pause:
            if self.proccess and self.proccess.isavlive():
                self.proccess.send(CTL_RESUME)
                self.state = Omxplayer.State_Play
    
    def lseek(self, step=False):
        raise NotImplementedError
        
    def rseek(self, step=False):
        raise NotImplementedError

    def stop(self):
        if self.state in (Omxplayer.State_Play, Omxplayer.State_Pause):
            if self.proccess and self.proccess.isavlive():
                self.proccess.send(CTL_QUIT)
                self.state = Omxplayer.State_Init
        
    def set_playlist(self, playlist):
        if self.state == Omxplayer.State_Init:
            self.playlist = playlist if playlist else []
    
    def add_playitem(self, item):
        if item:
            self.playlist.append(item)
        
    def del_playitem(self, item):
        self.playlist.remove(item)

    def set_dev(self, dev):
        if not dev in (Omxplayer.DEV_HDMI, Omxplayer.DEV_LOCAL):
            return
        if self.state == Omxplayer.State_Init:
            self.dev = dev

        
#singleton instance
player = Omxplayer()

class VirtualKey:
    
    #supported keys
    XK_Left     = 0xff51  
    XK_Up       = 0xff52  
    XK_Right    = 0xff53  
    XK_Down     = 0xff54  
    
    XK_p        = 0xff70
    XK_q        = 0xff71
    
    
    def __init__(self):
        self.syms = [key for key in dir(VirtualKey) if key.startswith('XK_')]
        self.values = [getattr(VirtualKey, key) for key in dir(VirtualKey) if key.startswith('XK_')]
        pass
    
    def list(self):
        print 'VirtualKey.list'
        return zip(self.syms, self.values)
        
    def key(self, *syms):
        print 'VirtualKey.key'
        if not set(syms).issubset(self.values):
            print 'unknow key symbols.'
            return
        print syms
        
#singleton instance
vk = VirtualKey()

if __name__ == '__main__':
    print vk.list()
    vk.key(VirtualKey.XK_Left, VirtualKey.XK_Right)
    vk.key(1, 2)
    vk.key(None)
    
    player.set_playlist(['1.mp3','2.mp3','3.mp3'])
    print player.playlist
    player.play()
    player.add_playitem('5.mp3')
    import time;time.sleep(1)
    player.stop()
    
    lm = LocalFile()
    for i in lm.list(LocalFile.VIDEO_ROOTPATH, LocalFile.VIDEO_FORMATS): print i,
