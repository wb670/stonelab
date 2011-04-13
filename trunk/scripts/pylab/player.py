#!/usr/bin/env python

import gst
import gobject
import sys
#to avoid eclipse'warning
eval('gobject.threads_init()') 
from threading import Thread

class AudioPlayer:
    
    EVENT_PLAY_NEW = 1
    
    def __init__(self, advisor):
        self.main = gobject.MainLoop()
        self.player = gst.element_factory_make('playbin', 'player')
        self.index = -1
        self.list = None
        self.advisor = advisor
        
        bus = self.player.get_bus()
        bus.add_signal_watch()
        bus.connect('message', self.on_message)
        
        Thread(target=self.main.run).start()
       
    def add_list(self , list=[]):
        if list is None:
            list = []
        self.list = [(i, l.strip(), l[l.rfind('/') + 1:]) for (i, l) in enumerate(list)]
        
    def play(self, index=None):
        #play specified tracks
        if 0 <= index < len(self.list):
            self.index = index
            self.player.set_state(gst.STATE_NULL)
            self.player.set_property('uri', self.list[index][1])
            self.player.set_state(gst.STATE_PLAYING)
            if self.advisor:
                self.advisor.on_message(AudioPlayer.EVENT_PLAY_NEW, (self.index, self.get_title()))
        #resume playing
        if index is None:
            if self.index > -1:
                self.player.set_state(gst.STATE_PLAYING)
    
    def pause(self):
        self.player.set_state(gst.STATE_PAUSED)
        
    def stop(self):
        self.player.set_state(gst.STATE_NULL)
        self.main.quit()
    
    def get_title(self):
        if self.index == -1 or len(self.list) == 0:
            return None
        return self.list[self.index][2] 
    
    def get_previous(self):
        if self.index == -1 or len(self.list) == 0:
            return - 1
        if self.index == 0:
            return 0
        return self.index - 1
    
    def get_next(self):
        if  len(self.list) == 0:
            return - 1
        if self.index + 1 == len(self.list):
            return 0
        return self.index + 1
    
    def on_message(self, bus, message):
        t = message.type
        if t == gst.MESSAGE_ERROR:
            self.play(self.get_next())
        elif t == gst.MESSAGE_EOS:
            self.play(self.get_next())

class Console:
    
    def __init__(self, list):
        self.player = AudioPlayer(self)
        self.player.add_list(list)
        self.player.play(0)

        Thread(target=self.run).start()
        
    def run(self):
        while(True):
            self.on_cmd(raw_input())
    
    def on_cmd(self, cmd):
        if cmd is None:
            return
        if cmd.startswith('play'):
            self.player.play()
        elif cmd.startswith('next'):
            self.player.play(self.player.get_next())
        elif cmd.startswith('previous'):
            self.player.play(self.player.get_previous())
        elif cmd.startswith('pause'):
            self.player.pause()
        elif cmd.startswith('list'):
            print '====================================='
            for info in self.player.list:
                print '%s. %s' % (info[0], info[2])
            print '====================================='
        elif cmd.startswith('info'):
            print '====================================='
            print '%s. %s' % (self.player.index, self.player.get_title())
            print '====================================='
        elif cmd.startswith('stop'):
            self.player.stop()
            sys.exit(0)
        elif cmd.startswith('dump'):
            from meliae import scanner
            scanner.dump_all_objects('./dump.txt')
        else:
            print '''=====================================
Usage:
play
next
previous
pause
list
info
stop
dump
====================================='''
    
    def on_message(self, event, info):
        if event == AudioPlayer.EVENT_PLAY_NEW:
            print '====================================='
            print 'Tracks: %s.%s' % (info[0], info[1])
            print '====================================='


if len(sys.argv) != 2:
    print 'player.py mp3.list'
    sys.exit(-1)
list = [l.strip() for l in open(sys.argv[1]).readlines() if l.strip() != '']
Console(list)
