#!/bin/sh
cd $(dirname $_)/..
BASE=`pwd`

#install 3rd lib
sudo apt-get install python-webpy
sudo apt-get install python-jinja2
sudo apt-get install python-pexpect

#install required dirs
mkdir $BASE/logs

chmod +x $BASE/bin/*
