#!/bin/sh
cd $(dirname $_)/..
BASE=`pwd`

python raspctl.py 8000 1>>$BASE/logs/raspctl.log 2>&1 &
