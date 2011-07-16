#master dns
from hosts import Hosts
from master_dns import MasterDns
from proxy_dns import ProxyDnsServer
import optparse
import platform
import sys

#get cmd options
parser = optparse.OptionParser()
parser.add_option('-f', '--hosts-file', dest='hosts', metavar='FILE', help='hosts file, default is /etc/hosts')
parser.add_option('-s', '--dns', dest='dns', metavar='SERVER', help='main dns server address.')
opts, args = parser.parse_args()

if not opts.dns:
    parser.print_help()
    sys.exit(-1)
    
if not opts.hosts:
    if platform.system() == 'Linux':
        opts.hosts = '/etc/hosts'
    elif platform.system() == 'Windows':
        opts.hosts = 'c:/windows/system32/drivers/etc/hosts'
    else:
        parser.print_help()
        sys.exit(-1)
        
local = ('127.0.0.1', 53)
hosts = opts.hosts
dns = [(dns, 53)  for dns in opts.dns.split(',')]

master = MasterDns(dns, 3)
hosts = Hosts(hosts, '.', '.')
proxy = ProxyDnsServer(local, master, hosts)
proxy.serve_forever()
