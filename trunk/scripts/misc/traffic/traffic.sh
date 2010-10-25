#!/bin/sh

SESSION_ID="kb1e2i45nvrmhofo2iryylza"
AGENT="Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.2.10) Gecko/20100915 Ubuntu/10.04 (lucid) Firefox/3.6.10"
IMAGE_CODE_URL="http://www.hzti.com/government/CreateCheckCode.aspx"
TRAFFIC_URL="http://www.hzti.com/service/qry/violation_veh.aspx?pgid=&type=1&node=249"

TMP_CODE_BASE="./tmp/code.gif"
TMP_CONTENT_BASE="./tmp/content.txt"
HIS_CONTENT_BASE="./tmp/his.txt"

idcode="239837"
no="浙AE856D"

curl -s -b "ASP.NET_SessionId=$SESSION_ID" "$IMAGE_CODE_URL" -o $TMP_CODE_BASE
code=`python lib/img_cracker.py $TMP_CODE_BASE`

. conf/config.sh

curl -s -A "$AGENT" -e $TRAFFIC_URL -b "ASP.NET_SessionId=$SESSION_ID;isLoginedWeb=T;ImageV=$code" -d "$QUERY_PARAM" "$TRAFFIC_URL" -o $TMP_CONTENT_BASE

now=`cat $TMP_CONTENT_BASE | grep -r '<td class="xxcxsspoptds">' | sed 's/<[^>]*>//g' | sed 's/\s\+/ /g'`
his=`cat $HIS_CONTENT_BASE | grep -r '<td class="xxcxsspoptds">' | sed 's/<[^>]*>//g' | sed 's/\s\+/ /g'`

if [ "$now" != "$his" ];then
    msg=`cat $TMP_CONTENT_BASE | grep -r '<td class="xxcxsspoptds">' | sed 's/<[^>]*>//g' | sed 's/\s\+/ /g' | head -n 6`
    echo $msg
fi
   

scp -r $TMP_CONTENT_BASE $HIS_CONTENT_BASE
