#!/bin/sh
cert_base="/home/stone/data/personal/cert"
cacert="$cert_base/intranet.crt"
cert="$cert_base/jinli.pem"
if [ "$1" == "" ];then
    echo 'staff.sh name'
    exit
fi
curl -sE $cert --cacert $cacert "https://www.cn.alibaba-inc.com/staffinfo.nsf/EmbedAll_Search_Asyn?SearchView&Query=$1&SearchOrder=4&" | grep -Pir 'td\d_' | sed 's/<[^>]*>/ /g' | sed 's/com/com\n/g' | sed 's/工号.*邮箱地址//g' | sed 's/\s\+/  /g' | sed 's/^\s\+//g' | sed 's/&nbsp//g'
