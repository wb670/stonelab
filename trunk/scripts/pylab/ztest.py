#!/usr/bin/python
from socket import socket, AF_INET, SOCK_DGRAM, SO_BROADCAST, SOL_SOCKET, SO_REUSEADDR

packet = b''
packet += b'\x01'   #Message type: Boot Request (1)
packet += b'\x01'   #Hardware type: Ethernet
packet += b'\x06'   #Hardware address length: 6
packet += b'\x00'   #Hops: 0 
packet += b'\x48\x26\x86\x66'       #Transaction ID
packet += b'\x00\x00'    #Seconds elapsed: 0
packet += b'\x00\x00'    #Bootp flags: 0x8000 (Broadcast) + reserved flags
packet += b'\x00\x00\x00\x00'   #Client IP address: 0.0.0.0
packet += b'\x00\x00\x00\x00'   #Your (client) IP address: 0.0.0.0
packet += b'\x00\x00\x00\x00'   #Next server IP address: 0.0.0.0
packet += b'\x00\x00\x00\x00'   #Relay agent IP address: 0.0.0.0
packet += b'\x00\x26\xc6\x64\x13\x56'   #Client MAC address: 00:26:9e:04:1e:9b //TODO
packet += b'\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00'   #Client hardware address padding: 00000000000000000000
packet += b'\x00' * 64  #Server host name not given
packet += b'\x00' * 128 #Boot file name not given
packet += b'\x63\x82\x53\x63'   #Magic cookie: DHCP

packet += b'\x35\x01\x03'   #Option: (t=53,l=1) DHCP Message Type = DHCP Discover
packet += b'\x32\x04\x0a\x10\xd4\x86'   #Option: (t=61,l=6) Client identifier
packet += b'\x0c\x05\x73\x74\x6f\x64\x66'
packet += b'\x37\x11\x01\x1c\x02\x03\x0f\x06\x77\x0c\x2c\x2f\x1a\x79\x2a\x79\xf9\xfc\x2a'
packet += b'\xff'   #End Option

sock = socket(AF_INET, SOCK_DGRAM)
sock.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
sock.setsockopt(SOL_SOCKET, SO_BROADCAST, 1)
sock.bind(('0.0.0.0', 68))
sock.settimeout(3)
sock.sendto(packet, ('<broadcast>', 67))
data = sock.recv(1024)
open('/home/stone/tmp/resp1.bin', 'wrb').write(data)
sock.close()
