#!/bin/sh
cert_base="/Users/stone/Documents/私人"
cert="$cert_base/liwang.pem"
if [ "$1" == "" ];then
    echo 'staff.sh name'
    exit
fi
curl -sE $cert -k "https://www.cn.alibaba-inc.com/search.php?key=$1" | grep -i 'td\d_' | gsed 's/<[^>]*>/ /g' | gsed 's/com/com\n/g' | gsed 's/工号.*邮箱地址//g' | gsed 's/\s\+/  /g' | gsed 's/^\s\+//g' | gsed 's/&nbsp//g'
