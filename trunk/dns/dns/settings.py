'''
Created on 2011-5-24

@author: stone
'''

# is debug
DEBUG = False

# master dns server
MASTER_DNS = (
    #('10.20.0.97', 53),
    #('10.20.0.98', 53),
    #('218.108.248.228', 53),
    ('218.108.248.245', 53),
)

# proxy dns server
PROXY_DNS = ('127.0.0.1', 53)

# time out 
MASTER_DNS_TIMEOUT = 3

# base hosts directory
BASE_HOSTS_DIR = '/home/stone/work/dev/stonelab/dns/hosts/'
# base ip directory
BASE_IPS_DIR = '/home/stone/work/dev/stonelab/dns/ips/' 
