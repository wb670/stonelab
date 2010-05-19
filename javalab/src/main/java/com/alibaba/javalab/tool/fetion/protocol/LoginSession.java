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

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * @author li.jinl
 */
public class LoginSession {

    private boolean logined;
    private String  ssic;
    private String  uri;
    private String  status;
    private String  sid;

    public boolean isLogined() {
        return logined;
    }

    public void setLogined(boolean logined) {
        this.logined = logined;
    }

    public String getSsic() {
        return ssic;
    }

    public void setSsic(String ssic) {
        this.ssic = ssic;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this).toString();
    }

}
