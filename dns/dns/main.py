'''
Created on 2011-5-24
@author: stone
'''
from cmd import CmdServer
from settings import CMD_SERVER
from hosts import Hosts
from master_dns import MasterDns
from proxy_dns import ProxyDnsServer
from settings import BASE_HOSTS_DIR, BASE_IPS_DIR, MASTER_DNS, \
    MASTER_DNS_TIMEOUT, PROXY_DNS

#master dns
master = MasterDns(MASTER_DNS, MASTER_DNS_TIMEOUT)
#hosts manager
hosts = Hosts(BASE_HOSTS_DIR, BASE_IPS_DIR)
#proxy dns
proxy = ProxyDnsServer(PROXY_DNS, master, hosts)
#cmd server
cmd = CmdServer(CMD_SERVER, hosts)
# start server
proxy.serve_forever()
cmd.serve_forever()
