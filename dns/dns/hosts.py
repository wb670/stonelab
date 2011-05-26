'''
Created on 2011-5-24
@author: stone
'''
import os

class Hosts(object):

    def __init__(self, base_file, host_dir, ip_dir):
        self.base_file = base_file
        self.host_dir = host_dir
        self.ip_dir = ip_dir
        
        self.repository_base = [] 
        self.repository_hosts = {} #{hosts_name:[(domain,ip)]}
        self.repository_ip = {}    #{ip:hosts_name}
        
        self.load_base()
        self.load_all_hosts()
        self.load_all_ip()
    
    def get_ip(self, ip, domain):
        if self.repository_ip.get(ip):
            ret = self._get_ip(domain, self.repository_hosts.get(ip)) # load from special hosts
        if not ret:
            ret = self._get_ip(domain, self.repository_base)          # load from base hosts
        return ret

    def _get_ip(self, domain, hosts_list):
        for host in hosts_list:
            if host[0].startswith('*'):
                    if domain.endswith(host[0][2:]):
                        return host[1]
            else:
                if host[0] == domain:
                    return host[1]
        return None
    
    def load_base(self):
        self.repository_base.extend(self._load_hosts(self.base_file))
        
        
    def load_all_ip(self):
        files = [f for f in os.listdir(self.ip_dir) if not f.startswith(".")]
        for i in files:
            self.load_ip(i)

    def load_ip(self, ip):
        self.repository_ip[ip] = open(self.ip_dir + ip).read()
    
    def load_all_hosts(self):
        files = [f for f in os.listdir(self.host_dir) if not f.startswith(".")]
        for i in files:
            self.load_hosts(i)
    
    def load_hosts(self, hosts_name):
        self.repository_hosts[hosts_name] = self._load_hosts(self.host_dir + hosts_name)

    def _load_hosts(self, file):
        # get hosts line
        lines = [line.strip() for line in open(file) if line.strip() != '' and not line.strip().startswith('#')]
        # get domain and ip 
        domains = []
        for line in lines:
            info = line.split()
            domains.extend([(h.strip(), info[0].strip()) for h in info[1:] if not h.strip().startswith("#") ])
        return domains
