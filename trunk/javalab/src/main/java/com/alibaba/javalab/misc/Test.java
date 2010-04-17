/**
 * Function: 
 * 
 * File Created at 2010-3-5
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.misc;

/**
 * @author li.jinl
 */
public class Test {

    private static final int           COUNT = 10000000;
    private static final Class<Object> CLASS = Object.class;

    public static void main(String[] args) throws Exception {
        Test.testNew();
        Test.testReflection();
    }

    public static void testNew() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            new Object();
        }
        long end = System.currentTimeMillis();
        System.out.println("new:" + (end - start));
    }

    public static void testReflection() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            CLASS.newInstance();
        }
        long end = System.currentTimeMillis();
        System.out.println("reflection:" + (end - start));
    }

}
