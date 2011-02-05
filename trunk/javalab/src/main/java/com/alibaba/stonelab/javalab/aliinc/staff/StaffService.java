/*
 * Copyright 1999-2011 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.aliinc.staff;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.stonelab.javalab.common.http.HttpClient;
import com.alibaba.stonelab.javalab.common.http.Response;

/**
 * staff service
 * 
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-1-21
 */
public class StaffService {

    private static final String  SEARCH_URL       = "https://www.cn.alibaba-inc.com/staffinfo.nsf/Search?SearchView&Query={0}&SearchOrder=4";
    private static final String  DEFAULT_ENCODING = System.getProperty("file.encoding");

    private static final String  REGEX            = "vwicnsr7.*?>(\\d+)</a>.*?>([\u4e00-\u9fa5]*)</a>.*?>([-\\d\\w\u4e00-\u9fa5]*)</a>.*?<font color=blue>([-\\d\\w\u4e00-\u9fa5，]*)</font>.*?face=\"宋体\">([-\\d\\w\u4e00-\u9fa5，]*)</font>.*?mailto:(.*?)>.*?uid=(.*?)&site=cn.*?(\\d{2}/\\d{2}/\\d{4})";
    private static final Pattern pattern          = Pattern.compile(REGEX);

    private HttpClient           httpClient;

    public List<Staff> search(String key) {
        List<Staff> list = new ArrayList<Staff>();
        Response res = httpClient.get(buildResourceUrl(key));
        if (res.getCode() != 200) {
            throw new StaffException("get http response fail.");
        }
        String content = "";
        try {
            content = new String(res.getContent(), res.getEncoding()).replace("\n", "");
        } catch (UnsupportedEncodingException e) {
        }
        Matcher m = pattern.matcher(content);
        while (m.find()) {
            Staff staff = new Staff();
            for (int i = 0; i < m.groupCount(); i++) {
                staff.setJobNumber(m.group(1));
                staff.setName(m.group(2));
                staff.setDepartment(m.group(3));
                staff.setExtension(m.group(4));
                staff.setMobile(m.group(5));
                staff.setEmail(m.group(6));
                staff.setAliww(m.group(7));
                staff.setJoinDate(m.group(8));
            }
            list.add(staff);
        }

        return list;
    }

    private String buildResourceUrl(String key) {
        String url = null;
        try {
            url = MessageFormat.format(SEARCH_URL, URLEncoder.encode(key, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            try {
                url = MessageFormat.format(SEARCH_URL, URLEncoder.encode(key, DEFAULT_ENCODING));
            } catch (UnsupportedEncodingException e1) {
                // ignore,this shold not be ocurred.
            }
        }
        return url;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * staff exception
     * 
     * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-1-21
     */
    public static class StaffException extends RuntimeException {

        private static final long serialVersionUID = -5942795922891168742L;

        public StaffException(String msg){
            super(msg);
        }

        public StaffException(String msg, Throwable e){
            super(msg, e);
        }

    }

}
