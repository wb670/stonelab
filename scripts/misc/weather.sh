#!/bin/sh
msg=`curl -s "http://weather.mzoe.com/city/58457.htm" | grep -r "class=\"item\"" -A 5 | grep -r "date\|desc\|temp" | sed 's/<[^>]*>//g' | sed 's/\s//g' | sed 's/[0-9]\+月//g' | sed 's/(.*)//g'`
echo $msg
