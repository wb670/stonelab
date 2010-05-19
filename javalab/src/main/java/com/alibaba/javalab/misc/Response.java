/**
 * Function: 
 * 
 * File Created at 2010-4-19
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.misc;

/**
 * @author li.jinl
 */
public class Response<T> {

    private boolean success;    //状态
    private String  code;       //返回码
    private String  description; //返回码描述
    private T       body;       //业务对象

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

}
