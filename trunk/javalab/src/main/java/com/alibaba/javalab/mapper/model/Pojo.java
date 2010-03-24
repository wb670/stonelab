/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.javalab.mapper.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * @author Maurice.J Nov 18, 2009 11:10:33 AM
 */
public class Pojo {

    private int     id;
    private String  name;
    private Version version;

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this).toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

}
