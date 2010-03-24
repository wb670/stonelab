/**
 * Function: 
 * 
 * File Created at 2010-2-21
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.asm;

import java.io.Serializable;

/**
 * @author li.jinl
 */
@Deprecated
public class MyObject implements Serializable {

    private static final long   serialVersionUID   = -3068383932334657297L;

    private static final int    DEFAULT_ID_VALUE   = 1;
    private static final String DEFAULT_NAME_VALUE = "default";

    private int                 id;
    private String              name;

    public MyObject() {
        id = DEFAULT_ID_VALUE;
        name = DEFAULT_NAME_VALUE;
    }

    public MyObject(int id, String name) throws RuntimeException {
        this.id = id;
        this.name = name;
    }

    @Deprecated
    public String greet() {
        return greet2();
    }

    protected String greet1() {
        return greet2();
    }

    private String greet2() {
        return (id + ":" + name);
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

}
