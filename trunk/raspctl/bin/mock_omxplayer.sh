#!/bin/sh
cd $(dirname $_)/..
BASE=`pwd`
if [ ! -d $BASE/logs ];then
    mkdir $BASE/logs
fi

$BASE/bin/mock_omxplayer >> $BASE/logs/mock_omxplayer.log
