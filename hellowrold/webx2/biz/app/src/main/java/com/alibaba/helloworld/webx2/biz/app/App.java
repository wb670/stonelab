package com.alibaba.helloworld.webx2.biz.app;

import com.alibaba.common.lang.StringUtil;

/**
 * Hello world!
 */
public class App {

    /**
     * ����һ�������ַ���
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
