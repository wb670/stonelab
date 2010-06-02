/**
 * Function: 
 * 
 * File Created at 2010-5-19
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.misc;

/**
 * @author li.jinl
 */
public class DefaultDemoService implements DemoService {

    @Override
    public String hello(String name) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        return "Welcome " + name;
    }

}
