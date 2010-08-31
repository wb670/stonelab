/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.learning.misc;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.EncodingUtil;

/**
 * @author Stone.J 2010-8-31 上午09:57:39
 */
public class Test {

    private static final String RESOURCE = "http://ezra-dev.alibaba-inc.com:7001/trustpass/promotion_join.htm";

    public static void main(String[] args) throws Exception {
        HttpClient httpClient = new HttpClient();
        GetMethod req = new GetMethod(RESOURCE);
        req.addRequestHeader("Content-Type", "text/html;charset=GBK");
        NameValuePair[] params = new NameValuePair[3];
        params[0] = new NameValuePair("memberID", "athena2002");
        params[1] = new NameValuePair("conName", "金立");
        params[2] = new NameValuePair("custName", "阿里巴巴");
        req.setQueryString(EncodingUtil.formUrlEncode(params, "GBK"));
        httpClient.executeMethod(req);
        String result = req.getResponseBodyAsString();
        req.releaseConnection();
        System.out.println(result);
    }
}
