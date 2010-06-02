/**
 * Function: 
 * 
 * File Created at 2010-6-2
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.spring.lazy;

/**
 * @author li.jinl
 */
public class TestBean {

    public void init() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            //ignore
            System.out.println(e);
        }
        System.out.println("TestBean Init");
    }

}
