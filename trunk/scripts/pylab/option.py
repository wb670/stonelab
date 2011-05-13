#!/usr/bin/python
import optparse
parser = optparse.OptionParser()
parser.add_option('-f', '--hosts-file', dest='hosts_file', metavar='<file>', default='/etc/hosts', help='specify hosts file, default /etc/hosts')
parser.add_option('-H', '--host', dest='host', default='127.0.0.1', help='specify the address to listen on')
parser.add_option('-p', '--port', dest='port', default=53, type='int', help='specify the port to listen on')
parser.add_option('-s', '--server', dest='dns_server', metavar='SERVER', help='specify the delegating dns server')
parser.add_option('-C', '--no-cache', dest='disable_cache', default=False, action='store_true', help='disable dns cache')
opts, args = parser.parse_args()
parser.print_help()
print opts, args
