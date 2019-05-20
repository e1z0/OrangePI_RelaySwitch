#!/usr/bin/python2.7
# RelayUI Embedded for Raspberry PI TFT 3.5"
# (c) 2018-2019 justinas@eofnet.lt
# Make sure you are already enabled the kernel I2C module on your Raspberry PI
# INSTALL
# apt-get install python-requests python-pygame autoconf libtool i2c-tools evtest tslib libts-bin libsdl1.2-dev libsdl-image1.2-dev libsdl-ttf2.0-dev
# git clone https://github.com/fictorial/pygameui.git&&cd pygameui&&easy_install .
# git clone https://github.com/libts/tslib.git&&autoreconf -vi&&./configure&&make&&make install
# sudo cp -P /usr/local/lib/libts* /usr/lib/arm-linux-gnueabihf/&&i2cdetect -y 1
# apply fix from: http://wiki.eofnet.lt/wiki/TFT_3.5_LCD_Raspberry#pygameui_fix
# Calibrate
# TSLIB_TSDEVICE=/dev/input/event0 TSLIB_FBDEVICE=/dev/fb1 TSLIB_CONFFILE=/etc/ts.conf TSLIB_PLUGINDIR=/usr/lib/ts ts_calibrate 
# Test
# TSLIB_TSDEVICE=/dev/input/event0 TSLIB_FBDEVICE=/dev/fb1 TSLIB_CONFFILE=/etc/ts.conf TSLIB_PLUGINDIR=/usr/lib/ts ts_test
# Usage
# TSLIB_TSDEVICE=/dev/input/event0 TSLIB_FBDEVICE=/dev/fb1 TSLIB_CONFFILE=/etc/ts.conf TSLIB_PLUGINDIR=/usr/lib/ts ./relaySwitch.py 
API="http://192.168.254.102:1415/"
DEBUG=1
import requests
import json
import sys
import pygame
import os
import pygameui as ui
import logging
import signal
import threading
import time
 
log_format = '%(asctime)-6s: %(name)s - %(levelname)s - %(message)s'
console_handler = logging.StreamHandler()
console_handler.setFormatter(logging.Formatter(log_format))
logger = logging.getLogger()
logger.setLevel(logging.DEBUG)
logger.addHandler(console_handler)
 
os.putenv('SDL_FBDEV', '/dev/fb1')
os.putenv('SDL_MOUSEDRV', 'TSLIB')
os.putenv('SDL_MOUSEDEV', '/dev/input/event0')
 
MARGIN = 30

relays = dict()
class Relay:
    def __init__(self, name, port, state):
        self.name = name
        self.port = port
        self.state = state

def relay_port(rl):
    return str(relays[rl].port)

def relay_state(rl):
    return str(relays[rl].state)

def TurnOver(name,port,btn):
    if relays[name].state == 1:
      relays[name].state = 0
      response = requests.get(API+'turn/'+str(port)+'/on')
      btn.background_color = (64, 64, 62)
    else:
      relays[name].state = 1
      response = requests.get(API+'turn/'+str(port)+'/off')
      btn.background_color = ((255, 255, 255), (192, 192, 192))

def GetDevices():
   response = requests.get(API+'devices')
   data = json.loads(response.content)
   return data

class PiTft(ui.Scene):
    def __init__(self):
        ui.Scene.__init__(self)
        LEFT=5
        TOP=5
        COUNT=0
        for switch in GetDevices():
           if "..." not in switch['name']:
             COUNT+=1
             LEFT = LEFT+35
             if COUNT > 8:
               TOP = TOP+320
               LEFT = 40
             relays[switch['name']] = Relay(switch['name'], switch['id'], switch['state'])
             self.buttonas = ui.Button(ui.Rect(TOP, LEFT, 300, 30), switch['name'])
             if switch['state'] == 0:
                logger.info(switch['name']+ ' yra ijungtas')
                self.buttonas.background_color = (64, 64, 62) # FIXME, does not change the color as necesarry
             self.buttonas.on_clicked.connect(self.gpi_button)
             self.add_child(self.buttonas)
 
    def gpi_button(self, btn, mbtn):
#        logger.info('Color: '+str(btn.background_color))
#        logger.info('We got presses on name: '+btn.text)
#        logger.info('We got presses on port: '+relay_port(btn.text))
        TurnOver(btn.text,relay_port(btn.text),btn)
 
ui.init('Raspberry Pi UI', (480, 320))
pygame.mouse.set_visible(False)
pitft = PiTft()

# Start the thread running the callable
 
def signal_handler(signal, frame):
    print 'You pressed Ctrl+C!'
    sys.exit(0)

signal.signal(signal.SIGINT, signal_handler)

ui.scene.push(pitft)
ui.run()
