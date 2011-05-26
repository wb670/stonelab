'''
Created on 2011-5-24

@author: stone
'''

# is debug
DEBUG = True

#sys cmd password
SYS_PASSWORD = 'hello'

# master dns server
MASTER_DNS = (
    #('10.20.0.97', 53),
    #('10.20.0.98', 53),
    ('218.108.248.228', 53),
    ('218.108.248.245', 53),
)

# proxy dns server
PROXY_DNS = ('127.0.0.1', 53)

# master dns time out, in seconds
MASTER_DNS_TIMEOUT = 3

# base hosts file
BASE_HOSTS = '../db/hosts.base'
# special hosts directory
HOST_DIR = '../db/hosts/'
# ip directory
IP_DIR = '../db/ips/' 

#message entrance
CMD_SERVER = ('127.0.0.1', 5454)
