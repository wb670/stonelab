package cn.zeroall.cow.web.common.util;

public class SubStringUtil {

    public static String getSubStr(int maxLenth, String str) {
        if (str.length() >= maxLenth) {
            str = str.substring(0, maxLenth) + "&nbsp;&nbsp;......";
        }
        return str;

    }

}
