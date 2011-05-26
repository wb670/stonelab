'''
Created on 2011-5-24
@author: stone
'''
from cmd import CmdServer
from hosts import Hosts
from master_dns import MasterDns
from proxy_dns import ProxyDnsServer
from settings import BASE_HOSTS, HOST_DIR, IP_DIR, MASTER_DNS, \
    MASTER_DNS_TIMEOUT, PROXY_DNS, CMD_SERVER
import threading

#master dns
master = MasterDns(MASTER_DNS, MASTER_DNS_TIMEOUT)
#hosts manager
hosts = Hosts(BASE_HOSTS, HOST_DIR, IP_DIR)
#proxy dns
proxy = ProxyDnsServer(PROXY_DNS, master, hosts)
#cmd server
cmd = CmdServer(CMD_SERVER, hosts, proxy)
cmd.daemon_threads = True
#start cmd server
threading.Thread(target=cmd.serve_forever, args=()).start()
# start dns server
proxy.serve_forever()
