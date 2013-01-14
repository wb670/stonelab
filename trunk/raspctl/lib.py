#!/usr/bin/env python
# encoding: utf-8
"""
lib.py

Created by stone on 2012-12-27.
Copyright (c) 2012 __MyCompanyName__. All rights reserved.
"""

from threading import Thread, Condition
from json import JSONEncoder
import os, pexpect, time

class LocalFile:
    MEDIA_ROOTPATH = '/home/pi/Media'
    
    AUDIO_FORMATS = ('mp3',)
    VIDEO_FORMATS = ('mkv', 'mp4',)
    MEDIA_FORMATS = AUDIO_FORMATS + VIDEO_FORMATS

    TYPE_DIR      = 'd'
    TYPE_FILE     = 'f'
    
    def __init__(self, encoding='UTF-8'):
        self.encoding = encoding
    
    def list(self, dir, formats, filter_dir=False):
        if not os.path.isdir(dir):
            None
        dir = os.path.abspath(dir)
        files = ['%s/%s' % (dir, self._unicode(media)) for media in os.listdir(dir) if os.path.isdir('%s/%s' % (dir, media)) ] if not filter_dir else []
        files = sorted(files) + sorted(['%s/%s' % (dir, self._unicode(media)) for media in os.listdir(dir) if '.' in media and media[media.rindex('.') + 1:] in formats]) 
        return [(f, LocalFile.TYPE_DIR if os.path.isdir(f) else LocalFile.TYPE_FILE ) for f in files]
    
    def list_all(self, dir, formats, filter_dir=False):
        if not os.path.isdir(dir):
            return None
        files = []
        for item in os.walk(dir):
            files.extend(self.list(self._unicode(item[0]), formats, False))
        return [f for f in files if f[1] == LocalFile.TYPE_FILE] if filter_dir else files

    def _unicode(self, f):
        if isinstance(f, unicode):
            return f
        try:
            return f.decode(self.encoding)
        except UnicodeEncodeError:
            return f

#singleton instance
local_file = LocalFile()
    

class Omxplayer:
    '''omxplayer cmd'''
    CMD = '/usr/bin/omxplayer -y -p -o %s "%s"' 

    '''omxplayer controllers'''
    CTL_QUIT        = 'q'
    CTL_PASUE       = 'p'
    CTL_RESUME      = 'p'
    CTL_LSEEK       = '\x1b[D' #30
    CTL_RSEEK       = '\x1b[C' #30
    CTL_FLSEEK      = '\x1b[B' #600
    CTL_FRSEEK      = '\x1b[A' #600


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
        if not 0 <= index < len(self.playlist):
            return
        if  not self.state == Omxplayer.State_Init:
            self.stop()
        if not loop == None: self.set_loop(loop)
        Thread(target=self._play, args=(index,)).start()
        #waiting for self.info info.
        twait = 0.0
        while(self.state != Omxplayer.State_Play or self.index != index):
            time.sleep(0.1); twait += 0.1
            if twait >= 2.0:
                break
        return

    
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
                    try:
                        if i >= len(self.playlist): break
                        self.process = pexpect.spawn(Omxplayer.CMD % (self.dev, self.playlist[i][0])) 
                        self.process.wait()
                        self.process.close()
                    except Exception as e:
                        print e
                        pass
                    if self.con.acquire():
                        self.con.notify()
                        self.con.release()
            #compensate. maybe playlist is updated while playing.
            if self.index < len(self.playlist):
                index = self.index + 1
                continue
            index = 0
            if len(self.playlist)==0 or not self.loop:
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

    def lseek(self, fast=False):
        if self.state == Omxplayer.State_Play:
            cmd = Omxplayer.CTL_FLSEEK if fast else Omxplayer.CTL_LSEEK
            self.process.send(cmd)
        
    def rseek(self, fast=False):
        if self.state == Omxplayer.State_Play:
            cmd = Omxplayer.CTL_FRSEEK if fast else Omxplayer.CTL_RSEEK
            self.process.send(cmd)

    def stop(self):
        if self.state in (Omxplayer.State_Play, Omxplayer.State_Pause):
            if self.process and self.process.isalive():
                self.process.send(Omxplayer.CTL_QUIT)
                self.state = Omxplayer.State_Init
                if self.process.isalive():
                    self.process.sendcontrol('C')
    
    def set_playlist(self, playlist):
        playlist = playlist if playlist else []
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
        if not 0 <= item < len(self.playlist):
            return
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
        self.loop = bool(loop)

    def get_info(self):
        info = dict(self.__dict__)
        del info['con']; del info['process']
        return info

#singleton instance
player = Omxplayer()


class JSONEncoderX(JSONEncoder):
    def default(self, obj):
        if hasattr(obj, '__dict__'):
            return getattr(obj, '__dict__')
        else:
            try:
                return JSONEncoder.default(self, obj)
            except:
                return None

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
