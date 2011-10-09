#!/bin/sh
war=$1
#TARGET='/home/admin/web-deploy/tomcat/webapps/ROOT'
TARGET='/home/stone/tmp/tester/'
rm -fr $TARGET
unzip $war -d $TARGET
