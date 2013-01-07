#!/usr/bin/env python
# encoding: utf-8
"""
lib.py

Created by stone on 2012-12-27.
Copyright (c) 2012 __MyCompanyName__. All rights reserved.
"""

from threading import Thread, Condition
from json import JSONEncoder
import os, pexpect

class LocalFile:
    AUDIO_ROOTPATH = '/home/pi/Media/Music'
    VIDEO_ROOTPATH = '/home/pi/Media/Movies'
    
    AUDIO_FORMATS = ('mp3',)
    VIDEO_FORMATS = ('mkv', 'mp4',)
    
    def __init__(self, encoding='UTF-8'):
        self.encoding = encoding
    
    def list(self, dir, formats, filter_dir=False):
        if not os.path.isdir(dir):
            return None
        files = ['%s/%s' % (dir, media.decode(self.encoding)) for media in os.listdir(dir) if os.path.isdir('%s/%s' % (dir, media)) ] if not filter_dir else []
        return sorted(files) + sorted(['%s/%s' % (dir, media.decode(self.encoding)) for media in os.listdir(dir) if media[media.rindex('.') + 1:] in formats]) 

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
        self.index      = 0
        self.state      = Omxplayer.State_Init
        self.loop       = False
        self.dev        = Omxplayer.DEV_HDMI
        self.process    = None
        self.con        = Condition()

    def play(self, index=0, loop=None):
        if  not self.state == Omxplayer.State_Init:
            self.stop()
        if not loop == None: self.set_loop(loop)
        Thread(target=self._play, args=(index,)).start()

    
    def _play(self, index):
        if not 0 <= index < len(self.playlist):
            return
        if not self.state == Omxplayer.State_Init:
            return
        #only one player process at the same time.
        if self.con.acquire():
            while(self.process and (not self.process.closed)):
                self.con.wait()
            self.con.release()
        #play 
        self.state = Omxplayer.State_Play
        while(self.state == Omxplayer.State_Play):
            for i in xrange(index, len(self.playlist)):
                if self.state == Omxplayer.State_Play:
                    self.index = i
                    self.process = pexpect.spawn(Omxplayer.CMD % (self.dev, self.playlist[i][1])) 
                    self.process.wait()
                    self.process.close()
                    if self.con.acquire():
                        self.con.notify()
                        self.con.release()
            index = 0
            if not self.loop:
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

    def prev(self):
        if not self.state == Omxplayer.State_Play:
            return
        index = self.index - 1 if self.index > 0 else 0
        self.play(index, self.loop)

    def next(self):
        if not self.state == Omxplayer.State_Play:
            return
        index = self.index + 1 if (self.index + 1) < len(self.playlist) else (len(self.playlist) - 1 if not self.loop else 0)
        self.play(index, self.loop)

    def lseek(self, step=False):
        raise NotImplementedError
        
    def rseek(self, step=False):
        raise NotImplementedError

    def stop(self):
        if self.state in (Omxplayer.State_Play, Omxplayer.State_Pause):
            if self.process and self.process.isalive():
                self.process.send(Omxplayer.CTL_QUIT)
                self.state = Omxplayer.State_Init
                if self.process.isalive():
                    self.process.sendcontrol('C')
    
    def set_playlist(self, playlist):
        if self.state == Omxplayer.State_Init:
            del self.playlist[:]
            for item in playlist:
                self.add_playitem(item)
    
    def add_playitem(self, item):
        if item: 
            self.playlist.append(item)
        
    def del_playitem(self, index):
        if 0 <= index < len(self.playlist):
            del self.playlist[index]
    
    def sort_playitem(self, item, offset, back=True):
        if back:
            to = (item + offset) if (item + offset) < len(self.playlist) else len(self.playlist) - 1
        else:
            to = (item - offset) if (item - offset) > 0 else 0
        self.playlist.insert(to, self.playlist.pop(item))

    def set_dev(self, dev):
        if not dev in (Omxplayer.DEV_HDMI, Omxplayer.DEV_LOCAL):
            return
        if self.state == Omxplayer.State_Init:
            self.dev = dev

    def set_loop(self, loop=False):
        self.loop = loop

    def get_playerinfo(self):
        return self.__dict__

#singleton instance
player = Omxplayer()


class JSONEncoderX(JSONEncoder):
    def default(self, obj):
        if hasattr(obj, '__dict__'):
            return getattr(obj, '__dict__')
        else:
            return JSONEncoder.default(self, obj)

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
    print local_file.list('/Users/stone/Movies', local_file.VIDEO_FORMATS, True)
