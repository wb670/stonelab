/*
 * Copyright 1999-2011 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com (\"Confidential Information\"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.misc;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.aliyun.common.auth.ServiceSignature;
import com.aliyun.common.comm.RequestMessage;
import com.aliyun.common.utils.HttpUtil;
import com.aliyun.openservices.ClientException;
import com.aliyun.openservices.HttpMethod;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.internal.OSSConstants;
import com.aliyun.openservices.oss.internal.OSSHeaders;
import com.aliyun.openservices.oss.internal.OSSUtils;
import com.aliyun.openservices.oss.internal.SignUtils;
import com.aliyun.openservices.oss.model.GeneratePresignedUrlRequest;

/**
 * @author <a href=\"mailto:li.jinl@alibaba-inc.com\">Stone.J</a> 2011-1-26
 */
public class Misc {

    private static final String ACCESS_ID  = "t3epvbWynK6EsP19";
    private static final String ACCESS_KEY = "E9QU0JrFIRhgXCY119nyl2eV8z488M";
    private static final String ENDPOINT   = "http://oss.aliyuncs.com";

    public static void main(String[] args) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, 10);
        Date d = cal.getTime();
        //d.setTime(1388131613 * 1000L);
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest("ace-bucket-2-0",
                "10089/6dac807b-af0d-435c-bf73-422d6ddfba15/upload.war");
        req.setExpiration(d);
        req.setMethod(HttpMethod.PUT);
        //req.addUserMetadata("leo", "application/zip");
        //req.addUserMetadata("Content-Type", "application/zip");
        System.out.println(generatePresignedUrl(req));
    }

    public static URL generatePresignedUrl(GeneratePresignedUrlRequest request) throws Exception {

        if (request.getBucketName() == null) {
            //throw new IllegalArgumentException(OSS_RESOURCE_MANAGER.getString("MustSetBucketName"));
        }
        if (request.getExpiration() == null) {
            //throw new IllegalArgumentException(OSS_RESOURCE_MANAGER.getString("MustSetExpiration"));
        }

        String bucketName = request.getBucketName();
        String key = request.getKey();

        String accessId = ACCESS_ID;
        String accessKey = ACCESS_KEY;
        HttpMethod method = request.getMethod() != null ? request.getMethod() : HttpMethod.GET;

        String expires = String.valueOf(request.getExpiration().getTime() / 1000L);
        String resourcePath = OSSUtils.makeResourcePath(key);

        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setEndpoint(OSSUtils.makeBukcetEndpoint(new URI(ENDPOINT), bucketName));
        requestMessage.setMethod(method);
        requestMessage.setResourcePath(resourcePath);
        requestMessage.addHeader("Date", expires);
        requestMessage.addHeader("Content-Type", "application/zip");
        for (Map.Entry<String, String> h : request.getUserMetadata().entrySet()) {
            requestMessage.addHeader(OSSHeaders.OSS_USER_METADATA_PREFIX + h.getKey(), h.getValue());
        }

        Map<String, String> responseHeadersParams = OSSUtils.getResponseHeaderParameters(request.getResponseHeaders());
        if (responseHeadersParams.size() > 0) {
            requestMessage.setParameters(responseHeadersParams);
        }

        String canonicalResource = "/" + ((bucketName != null) ? bucketName : "") + ((key != null ? "/" + key : ""));
        String canonicalString = SignUtils.buildCanonicalString(method.toString(), canonicalResource, requestMessage,
                expires);
        String signature = ServiceSignature.create().computeSignature(accessKey, canonicalString);

        Map<String, String> params = new HashMap<String, String>();
        params.put("Expires", expires);
        params.put("OSSAccessKeyId", accessId);
        params.put("Signature", signature);
        params.putAll(responseHeadersParams);

        // 生成URL
        String queryString = null;
        try {
            queryString = HttpUtil.paramToQueryString(params, OSSConstants.CHARSET);
        } catch (UnsupportedEncodingException e) {
            // throw new ClientException(OSS_RESOURCE_MANAGER.getString("FailedToEncodeUri"), e);
        }

        String url = requestMessage.getEndpoint().toString();
        if (!url.endsWith("/")) {
            url += "/";
        }
        url += resourcePath + "?" + queryString;

        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new ClientException(e);
        }
    }

}
