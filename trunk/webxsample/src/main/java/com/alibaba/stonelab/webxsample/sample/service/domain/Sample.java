/**
 * Function: 
 * 
 * File Created at 2010-7-16
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.stonelab.webxsample.sample.service.domain;

import java.io.Serializable;

/**
 * @author li.jinl
 */
public class Sample implements Serializable {

    private static final long serialVersionUID = 5521593012180530078L;

    private String            name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
