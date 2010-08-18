package com.alibaba.helloworld.webx2.biz.app;

import com.alibaba.common.lang.StringUtil;

/**
 * Hello world!
 */
public class App {

    /**
     * 产生一个回文字符串
     */
    public String getPalindrome(String str) {
        if (StringUtil.isEmpty(str)) {
            return str;
        } else {
            StringBuilder strBuilder = new StringBuilder(str);
            strBuilder.reverse();
            return str + strBuilder.toString();
        }
    }
}
