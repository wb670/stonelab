#!/bin/sh
BASE=$(dirname $0)/..
if [ ! -d $BASE/logs ];then
    mkdir $BASE/logs
fi
$BASE/bin/mock_omxplayer $1 $2 >>$BASE/logs/mock_omxplayer.log
