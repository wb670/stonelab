'''
Created on 2010-11-5

@author: stone
'''
import logging
from logging import config

config.fileConfig('logging.conf')
logger = logging.getLogger()