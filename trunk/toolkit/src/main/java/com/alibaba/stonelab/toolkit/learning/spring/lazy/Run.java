/**
 * Function: 
 * 
 * File Created at 2010-6-2
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.stonelab.toolkit.learning.spring.lazy;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author li.jinl
 */
public class Run {

    private static final String CONFIG = "classpath:spring/bean.xml";

    public static void main(String[] args) {
        testInit();
        System.out.println("===============================");
        testLazyInit();
    }

    public static void testInit() {
        new ClassPathXmlApplicationContext(CONFIG);
    }

    public static void testLazyInit() {
        new LazyInitClasspathXmlApplicationContext(CONFIG);
    }

}
