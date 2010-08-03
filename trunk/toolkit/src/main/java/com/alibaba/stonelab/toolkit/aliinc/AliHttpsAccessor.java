package com.alibaba.stonelab.toolkit.aliinc;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.protocol.Protocol;

public class AliHttpsAccessor {

    private HttpClient httpClient;

    public AliHttpsAccessor() {
        Protocol.registerProtocol("https", new Protocol("https", AliSSLContext.getEasySSLProtocolSocketFactory(), 443));
        httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
    }

    public String request(String url) {
        GetMethod req = new GetMethod(url);
        String ret = "";
        try {
            httpClient.executeMethod(req);
            ret = req.getResponseBodyAsString();
            req.releaseConnection();
        } catch (Exception e) {
            System.out.println(e);
        }
        return ret;
    }
}
