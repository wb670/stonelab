#!/bin/sh
cd $(dirname $_)/..
BASE=`pwd`

#install 3rd lib
sudo apt-get install python-webpy python-jinja2 python-pexpect

#install required dirs
mkdir $BASE/logs

chmod +x $BASE/bin/*
