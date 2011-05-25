'''
Created on 2011-5-24
@author: stone
'''
from hosts import Hosts
from master_dns import MasterDns
from proxy_dns import ProxyDnsServer
from settings import BASE_HOSTS_DIR, BASE_IPS_DIR, MASTER_DNS, \
    MASTER_DNS_TIMEOUT, PROXY_DNS

master = MasterDns(MASTER_DNS, MASTER_DNS_TIMEOUT)
hosts = Hosts(BASE_HOSTS_DIR, BASE_IPS_DIR)
proxy = ProxyDnsServer(PROXY_DNS, master, hosts)
proxy.serve_forever()
