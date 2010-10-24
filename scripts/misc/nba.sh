#!/bin/sh
to=13867494321
msg=`curl -s "http://nba.sports.sina.com.cn/js/2007live.js" | iconv -f 'gbk' -t 'utf-8' | tr ';' '\n' | grep -r 'var today' | tr '|' '\n' | gawk -F ',' '{if(NF>1)printf "[%s%s-%s%s]%s\\\\n",$2,$7,$8,$4,$10}'`
echo $msg
./fx.sh "$to" "\n$msg"
