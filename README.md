# OrangePI_RelaySwitch
Power Switching using 1-16 Relays and OrangePI (all OrangePI variations should work)
Hardware Requirements:
* Buy OrangePI2 Lite https://www.ebay.co.uk/itm/Orange-Pi-Lite-with-Quad-Core-1-2GHz-512MB-DDR3-WiFi-Mini-PC-Mali400MP2-GPU/272887180399?hash=item3f8957ec6f:g:~4cAAOSwS0lZ4ePd
* Buy 16 Relay Switch https://www.ebay.co.uk/itm/1-2-4-6-8-5V-Channel-Relay-Board-Module-Optocoupler-LED-for-Arduino-PiC-ARM-AVR-/273141723460?var=&hash=item3f9883f144

Software Requirements:
* Armbian

![testas](/IMG_20180711_143803.jpg) ![testas2](IMG_20180711_143815.jpg)
![test0](/IMG_20180714_231605.jpg)
![testas3](/android_app_pic.png)


## Prepare OrangePI
You need to have Armbian or compatible Linux distribution on your OrangePI, then you need to connect GPIO ports from Relay to you OrangePI, here is the scheme how to do this:
![test](/orangepi_pins.png)


![test2](/OPiLite_pinout.jpg)

![test3](/IMG_20180714_205435.jpg)


## Install GPIO Support library (designed especially for OrangePI)
```
apt install python-setuptools python-pip python-dev
pip install python-daemon bottle pyA20
git clone https://github.com/duxingkei33/orangepi_PC_gpio_pyH3
cd orangepi_PC_gpio_pyH3
sudo python setup.py install
```

## Install the relay switching daemon and it's dependencies
```
wget https://raw.githubusercontent.com/e1z0/OrangePI_RelaySwitch/master/relay_switch -O /usr/local/bin/relay_switch
wget https://raw.githubusercontent.com/e1z0/OrangePI_RelaySwitch/master/relayswitch.service -O /etc/systemd/system/relayswitch
chmod +x /usr/local/bin/relay_switch
systemctl daemon-reload
systemctl enable relayswitch
systemctl start relayswitch
```
Also you can use **/usr/local/bin/relay_switch** for external commands, it supports powering On/off the GPIO pinouts, turn on or off all the relays at the time and display them all.

You can also specify their names... Run command
```/usr/local/bin/relay_switch --ports``` to show all the ports, copy them to the **/etc/mods/settings** file and make names for them for example i have this list:
```GPIO_199=monitorius 1
GPIO_198=stalas
GPIO_201=taburete
GPIO_20=kazkokia rozete
GPIO_10=grindinis sildymas
GPIO_9=pecius
GPIO_200=staline lempa
GPIO_8=tulikos sviesa
GPIO_7=ceinykas
GPIO_2=virykle
GPIO_71=lauko sviesa
GPIO_68=veranda
GPIO_3=skladukas
GPIO_0=balkonas
GPIO_1=virtuves sviesa
GPIO_110=testas
```
It will be read each time, then the app request the list of the devices

**TODO:**
* Make auto discovery of such relays on the network (Multiple relay support)
* Edit name from the app or other interface
* Make web interface for interacting with the relays

*You can request more features too, just write in the issues tab...*

## Other projects/libraries for OrangePI that i found in github

* https://github.com/thk4711/orangepi-radio
* https://github.com/trebisky/orangepi
* https://github.com/FREEWING-JP/OrangePi_CedarX
* https://github.com/mvdiogo/opencv-orangepi
* https://github.com/evergreen-it-dev/orangepi
* https://github.com/koksalkapucuoglu/Orangepi-python
* https://github.com/ludiazv/node-nrf24
* https://github.com/najnesnaj/touchscreen-focaltech-opi
* https://github.com/OrangePiRobotics/ftc_app_master-2016-2017 - Robot code
* https://github.com/orangepi-xunlong/OrangePi2MicrosoftAzure
* https://github.com/thunderbom/ORANGEPI_Website
* https://github.com/tokarev25/craftbeerpi
* https://github.com/adnio/opi_miracle
* https://github.com/nopnop2002/ili9340spi_rpi
* https://github.com/mvdiogo/gpu_orangepi
* https://github.com/Alex2269/orangepi_tft
* https://github.com/Naminator/gpio-door
* https://github.com/hankso/OrangePi-BuildML
* https://github.com/KushlaVR/OrangePiWeb
