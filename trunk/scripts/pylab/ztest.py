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
        bus.enable_sync_message_emission()
        bus.connect('message', self.on_message)
        
        
        Thread(target=self.main.run).start()
       
    def add_list(self , list=[]):
        if list is None:
            list = []
        self.list = [(i, l, l[l.rfind('/') + 1:]) for (i, l) in enumerate(list)]
        
    def play(self, index=None):
        if 0 <= index < len(self.list):
            self.index = index
            self.player.set_state(gst.STATE_NULL)
            self.player.set_property('uri', self.list[index][1])
            self.player.set_state(gst.STATE_PLAYING)
            if self.advisor:
                self.advisor.on_message(AudioPlayer.EVENT_PLAY_NEW, (self.index, self.get_title()))
        if index is None:
            if self.index > -1:
                self.player.set_state(gst.STATE_PLAYING)
    
    def pause(self):
        self.player.set_state(gst.STATE_PAUSED)
        
    def stop(self):
        self.player.set_state(gst.STATE_NULL)
        self.main.quit()
    
    def get_title(self):
        if self.index == -1 \
            or len(self.list) == 0:
            return None
        title = self.list[self.index][1] 
        return title[title.rfind('/') + 1:]
    
    def get_next_index(self):
        if  len(self.list) == 0:
            return - 1
        if self.index + 1 == len(self.list):
            return 0
        return self.index + 1
    
    def on_message(self, bus, message):
        t = message.type
        if t == gst.MESSAGE_ERROR:
            self.play(self.get_next_index())
        elif t == gst.MESSAGE_EOS:
            self.play(self.get_next_index())

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
        elif cmd.startswith('pause'):
            self.player.pause()
        elif cmd.startswith('list'):
            for info in self.player.list:
                print '%s. %s' % (info[0], info[2])
        elif cmd.startswith('info'):
            print '%s. %s' % (self.player.index, self.player.get_title())
        elif cmd.startswith('stop'):
            self.player.stop()
            sys.exit(0)
    
    def on_message(self, event, info):
        if event == AudioPlayer.EVENT_PLAY_NEW:
            print 'New:%s.%s' % (info[0], info[1])


if len(sys.argv) != 2:
    print 

Console(list)