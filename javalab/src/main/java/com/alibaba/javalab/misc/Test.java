/**
 * Function: 
 * 
 * File Created at 2010-3-5
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.misc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * @author li.jinl
 */
public class Test implements Callable<Boolean> {

    private static final String URL        = "http://localhost:8080/weblab/statistics/party.htm";
    private static final int    GROUP      = 200;
    private static final int    COUNT      = 100;
    private static int          total      = 0;
    private static int          errorTotal = 0;

    private HttpClient          httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());

    public static void main(String[] args) throws Exception {
        Test t = new Test();
        ExecutorService threadPool = Executors.newFixedThreadPool(GROUP);
        long start = System.currentTimeMillis();
        for (int i = 0; i < GROUP; i++) {
            threadPool.submit(t).get();
        }
        long end = System.currentTimeMillis();
        threadPool.shutdown();
        System.out.println(total + ":" + errorTotal + ":" + (end - start));
    }

    public void request(String jobNumber, String name) throws Exception {
        PostMethod post = new PostMethod(URL);
        post.addParameter("jobNumber", jobNumber);
        post.addParameter("name", name);
        post.addParameter("form_name", "form");
        post.addParameter("submit", "确认");
        post.addRequestHeader("Referer", URL);
        httpClient.executeMethod(post);
        post.releaseConnection();
    }

    public Boolean call() throws Exception {
        for (int i = 0; i < COUNT; i++) {
            try {
                request(String.valueOf(total), String.valueOf(total));
            } catch (Exception e) {
                errorTotal++;
            }
            total++;
        }
        return true;
    }
}
