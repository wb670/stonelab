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
    AUDIO_ROOTPATH = '/home/pi/Media/Music'
    VIDEO_ROOTPATH = '/home/pi/Media/Movies'
    
    AUDIO_FORMATS = ('mp3',)
    VIDEO_FORMATS = ('mkv', 'mp4',)
    
    def __init__(self, encoding='UTF-8'):
        self.encoding = encoding
    
    def list(self, dir, formats):
        if not os.path.isdir(dir):
            return None
        return sorted(['%s/%s' % (dir, media.decode(self.encoding)) for media in os.listdir(dir) if media[media.rindex('.') + 1:] in formats]) 

#singleton instance
local_file = LocalFile()
    

class Omxplayer:
    '''omxplayer cmd'''
    CMD = '/usr/bin/omxplayer -p -o %s %s' 

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
        self.process   = None
    
    def play(self, loop=False):
        Thread(target=self._play, args=(loop,)).start()
    
    def _play(self, loop=False):
        if not self.state == Omxplayer.State_Init:
            return
        #play 
        self.state = Omxplayer.State_Play
        while(True):
            for media in self.playlist:
                if self.state == Omxplayer.State_Play:
                    self.process = pexpect.spawn(Omxplayer.CMD % (self.dev, media)) 
                    self.process.wait()
                    self.process.close()
            if not loop:
                break
        self.state = Omxplayer.State_Init
    
    def pause(self):
        if self.state == Omxplayer.State_Play:
            if self.process and self.process.isalive():
                self.process.send(Omxplayer.CTL_PASUE)
                self.state = Omxplayer.State_Pause
        
    def resume(self):
        if self.state == Omxplayer.State_Pause:
            if self.process and self.process.isalive():
                self.process.send(Omxplayer.CTL_RESUME)
                self.state = Omxplayer.State_Play
    
    def lseek(self, step=False):
        raise NotImplementedError
        
    def rseek(self, step=False):
        raise NotImplementedError

    def stop(self):
        if self.state in (Omxplayer.State_Play, Omxplayer.State_Pause):
            if self.process and self.process.isalive():
                self.process.send(Omxplayer.CTL_QUIT)
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


def for_test():
    m = lfile.list(lfile.VIDEO_ROOTPATH, lfile.VIDEO_FORMATS)
    player.set_playlist(m)
    player.play()
