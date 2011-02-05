/*
 * Copyright 1999-2011 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.common.http;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * HTTP Response Object
 * 
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-1-20
 */
public class Response {

    private static final String      TAG_CONTENT_TYPE = "content-type";
    private static final String      TAG_CHARSET      = "charset";
    private static final String      REGEX_JUNK       = "[\\s=<>;,\"]";

    private static final Set<String> ENCODINGS        = new HashSet<String>();
    private static final String      DEFAULT_ENCODING = "utf-8";

    static {
        Set<String> charsets = Charset.availableCharsets().keySet();
        for (String charset : charsets) {
            ENCODINGS.add(charset.toLowerCase());
            ENCODINGS.add("utf8");
            ENCODINGS.add("utf-8");
        }
    }

    // http response code
    private int                      code;
    // http response message
    private String                   message;
    // http response headers
    private Map<String, String>      headers          = new LinkedHashMap<String, String>();

    // http response content type
    private String                   type;
    // http response content encoding
    private String                   encoding;
    // http response content. i.e body info
    private byte[]                   content;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void addHead(String key, String value) {
        this.headers.put(key.toLowerCase(), value);
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getType() {
        if (this.type == null) {
            this.type = parseContentType();
        }
        return this.type;
    }

    public String getEncoding() {
        if (this.encoding == null) {
            this.encoding = parseContentEncoding();
        }
        return this.encoding;
    }

    // parse content type from header
    private String parseContentType() {
        if (headers == null) {
            return null;
        }
        return headers.get(TAG_CONTENT_TYPE);
    }

    // parse content encoding from content type
    private String parseContentEncoding() {
        String type = getType();
        if (type == null) {
            return DEFAULT_ENCODING;
        }
        String separator = TAG_CHARSET;
        int index = type.indexOf(separator);
        String encoding = type.substring(index + separator.length()).replaceAll(REGEX_JUNK, "");
        if (!ENCODINGS.contains(encoding.toLowerCase())) {
            return encoding;
        } else {
            return DEFAULT_ENCODING;
        }
    }

    @Override
    public String toString() {
        return "Response [code=" + code + ", message=" + message + ", headers=" + headers + ", encoding="
               + getEncoding() + ", type=" + getType() + ", content=" + content + "]";
    }

}
