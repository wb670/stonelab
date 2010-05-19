/**
 * Function: 
 * 
 * File Created at 2010-4-28
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.tool.fetion.protocol;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

/**
 * @author li.jinl
 */
public class FetionHelper {

    private HttpClient          httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());

    private static FetionHelper helper     = new FetionHelper();

    public static FetionHelper getFetionHelper() {
        return helper;
    }

    private FetionHelper() {
    }

    public String communicate(String url, String data) {
        PostMethod post = null;
        String content = null;
        try {
            post = new PostMethod(url);
            post.setRequestEntity(new StringRequestEntity(data, null, null));
            httpClient.executeMethod(post);
            content = post.getResponseBodyAsString();
        } catch (Exception e) {
            System.out.println(e);
        }
        post.releaseConnection();
        return content;
    }

    public Map<String, String> communicate(String url, Map<String, String> param) {
        Map<String, String> info = new HashMap<String, String>();
        PostMethod post = null;
        try {
            post = new PostMethod(url);
            if (param != null) {
                Set<Entry<String, String>> set = param.entrySet();
                for (Entry<String, String> entry : set) {
                    post.addParameter(entry.getKey(), entry.getValue());
                }
            }
            httpClient.executeMethod(post);

            info.put("body", post.getResponseBodyAsString());
            Header[] headers = post.getResponseHeaders();
            for (Header header : headers) {
                info.put(header.getName(), header.getValue());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        post.releaseConnection();
        return info;
    }
}
