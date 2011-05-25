'''
Created on 2011-5-24
@author: stone
'''
import os

class Hosts(object):

    def __init__(self, base_hosts, base_ips):
        self.base_hosts = base_hosts
        self.base_ips = base_ips
        self.repository_hosts = {} #{hosts_name:[(domain,ip)]}
        self.repository_ip = {}    #{ip:hosts_name}
        self.load_all_hosts()
        self.load_all_ip()
    
    def get_ip(self, ip, domain):
        if not self.repository_ip.get(ip):
            return None
        for host in self.repository_hosts.get(self.repository_ip.get(ip)):
            if host[0].startswith('*'):
                if domain.endswith(host[0][2:]):
                    return host[1]
            else:
                if host[0] == domain:
                    return host[1]
        return None
        
    def load_all_ip(self):
        files = os.listdir(self.base_ips)
        for i in files:
            self.load_ip(i)

    def load_ip(self, ip):
        self.repository_ip[ip] = open(self.base_ips + ip).read()
    
    def load_all_hosts(self):
        files = os.listdir(self.base_hosts)
        for i in files:
            self.load_hosts(i)
    
    def load_hosts(self, hosts_name):
        # get hosts line
        lines = [line.strip() for line in open(self.base_hosts + hosts_name) if line.strip() != '' and not line.strip().startswith('#')]
        # get domain and ip 
        domains = []
        for line in lines:
            info = line.split()
            domains.extend([(h.strip(), info[0].strip()) for h in info[1:] if not h.strip().startswith("#") ])
        self.repository_hosts[hosts_name] = domains
