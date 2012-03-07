/*
 * Copyright 1999-2011 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.common.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import com.alibaba.stonelab.javalab.common.util.IoUtil;

/**
 * <pre>
 * to simulate http/https client operators:
 * method: get/post
 * </pre>
 * 
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-1-20
 */
public class HttpClient {

    private static final int                 DEFAULT_CONNECTION_TIMEOUT = 2000;
    private static final int                 DEFAULT_READ_TIMEOUT       = 5000;
    private static final String              DEFAULT_ENCODING           = "UTF-8";
    private static final String              METHOD_GET                 = "GET";
    private static final String              METHOD_POST                = "POST";
    private static final Map<String, String> DEFAULT_HEADERS            = new HashMap<String, String>(32);

    static {
        DEFAULT_HEADERS.put("User-Agent", "Http Client/1.0");
    }

    private int                              connectionTimeout          = DEFAULT_CONNECTION_TIMEOUT;
    private int                              readTimeout                = DEFAULT_READ_TIMEOUT;
    private String                           encoding                   = DEFAULT_ENCODING;
    private Map<String, String>              headers                    = DEFAULT_HEADERS;

    private SocketFactoryContext             socketFactoryContext;

    public HttpClient(){
    }

    public HttpClient(SocketFactoryContext ctx){
        this.socketFactoryContext = ctx;
    }

    /**
     * http post request
     * 
     * @param url post request url
     * @param parameters post request parameters
     * @return http response
     */
    public Response post(String url, Map<String, String> parameters) {
        return post(url, null, parameters);
    }

    /**
     * http post request
     * 
     * @param url post request url
     * @param headers post request headers
     * @param parameters post request parameters
     * @return http response
     */
    public Response post(String url, Map<String, String> headers, Map<String, String> parameters) {
        URL u = null;
        try {
            u = new URL(url);
        } catch (MalformedURLException e) {
            throw new HttpClientException("url format error.", e);
        }
        if (!isHttpProtocol(u)) {
            throw new HttpClientException("protocol error. protocol is " + u.getProtocol());
        }
        Response res = null;
        try {
            // create http connection
            HttpURLConnection con = createConnection(u, METHOD_POST);
            // post data
            OutputStream out = con.getOutputStream();
            out.write(buildContent(parameters).getBytes());
            out.flush();
            IoUtil.close(out);
            // create http response
            res = createResponse(con);
            // close all
            con.disconnect();
        } catch (IOException e) {
            throw new HttpClientException("post error.", e);
        }

        return res;
    }

    /**
     * http get request
     * 
     * @param url get request url
     * @return http response
     */
    public Response get(String url) {
        return get(url, null);
    }

    /**
     * http get request
     * 
     * @param url get request url
     * @param headers get request headers
     * @return http response
     */
    public Response get(String url, Map<String, String> headers) {
        URL u = null;
        try {
            u = new URL(url);
        } catch (MalformedURLException e) {
            throw new HttpClientException("url format error.", e);
        }
        if (!isHttpProtocol(u)) {
            throw new HttpClientException("protocol error. protocol is " + u.getProtocol());
        }

        Response res = null;
        try {
            // create connection
            HttpURLConnection con = createConnection(u, METHOD_GET);
            // do connect
            con.connect();
            // return http response
            res = createResponse(con);
            // close all
            con.disconnect();
        } catch (IOException e) {
            throw new HttpClientException("http(get) error.", e);
        }

        return res;
    }

    // to merge request headers and default headers
    private Map<String, String> mergeHeaders(Map<String, String> headers) {
        Map<String, String> requestHeaders = new HashMap<String, String>(32);
        requestHeaders.putAll(this.headers);
        if (headers != null) {
            requestHeaders.putAll(headers);
        }
        return requestHeaders;
    }

    // build request parameters to content string
    private String buildContent(Map<String, String> parameters) throws UnsupportedEncodingException {
        if (parameters == null) {
            return null;
        }
        StringBuilder ret = new StringBuilder();
        Iterator<Entry<String, String>> it = parameters.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, String> e = it.next();
            ret.append(e.getKey()).append("=").append(URLEncoder.encode(e.getValue(), encoding));
            if (it.hasNext()) {
                ret.append("&");
            }
        }
        return ret.toString();
    }

    // if is http protocol,contains https protocol
    private boolean isHttpProtocol(URL url) {
        return "HTTP".equalsIgnoreCase(url.getProtocol()) || "HTTPS".equalsIgnoreCase(url.getProtocol());
    }

    // if is https protocol
    private boolean isHttpsProtocol(URL url) {
        return "HTTPS".equalsIgnoreCase(url.getProtocol());
    }

    // create http connection
    private HttpURLConnection createConnection(URL url, String method) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(method);
        con.setConnectTimeout(connectionTimeout);
        con.setReadTimeout(readTimeout);
        con.setDoInput(true);
        con.setDoOutput(true);
        // set http request headers
        Map<String, String> requestHeaders = mergeHeaders(headers);
        for (Entry<String, String> e : requestHeaders.entrySet()) {
            con.setRequestProperty(e.getKey(), e.getValue());
        }
        // set ssl socket factory if is https request
        if (isHttpsProtocol(url) && socketFactoryContext != null) {
            ((HttpsURLConnection) con).setSSLSocketFactory((SSLSocketFactory) socketFactoryContext.createSocketFactory());
        }
        return con;
    }

    // create http response
    private Response createResponse(HttpURLConnection con) throws IOException {
        Response res = new Response();
        // add http response headers
        Map<String, java.util.List<String>> responseHeaders = con.getHeaderFields();
        for (Entry<String, List<String>> e : responseHeaders.entrySet()) {
            if (e.getKey() != null) {
                res.addHead(e.getKey(), e.getValue().get(0));
            }
        }
        // add http status
        res.setCode(con.getResponseCode());
        res.setMessage(con.getResponseMessage());
        // add http content
        InputStream in = con.getInputStream();
        res.setContent(IoUtil.toBytes(in));
        IoUtil.close(in);
        return res;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public SocketFactoryContext getSocketFactoryContext() {
        return socketFactoryContext;
    }

    public void setSocketFactoryContext(SocketFactoryContext socketFactoryContext) {
        this.socketFactoryContext = socketFactoryContext;
    }

    public static final class HttpClientException extends RuntimeException {

        private static final long serialVersionUID = 783671263515841383L;

        public HttpClientException(String msg){
            super(msg);
        }

        public HttpClientException(String msg, Throwable e){
            super(msg, e);
        }

    }

}
