/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.learning.misc;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <pre>
 * Loop Count:10000000
 * from spring time: 1750 => 0.000175
 * from cache time:   187 => 0.0000187
 * </pre>
 * 
 * @author Stone.J 2010-8-3 下午06:11:31
 */
public class Misc {

    private static final String              LOCATION  = "classpath:learning/spring/bean.xml";
    private static final ApplicationContext  CTX       = new ClassPathXmlApplicationContext(LOCATION);
    private static final String              BEAN_NAME = "javaBean";
    private static final Map<String, Object> CACHE     = new HashMap<String, Object>();

    static {
        CACHE.put(BEAN_NAME, CTX.getBean(BEAN_NAME));
    }

    private static final int                 LOOP      = 10000000;

    public static void main(String[] args) throws Exception {
        testFromSpring();
        testFromCache();
    }

    public static void testFromSpring() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < LOOP; i++) {
            CTX.getBean(BEAN_NAME);
        }
        long dur = System.currentTimeMillis() - start;
        System.out.println("from spring time: " + dur);
    }

    public static void testFromCache() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < LOOP; i++) {
            CACHE.get(BEAN_NAME);
        }
        long dur = System.currentTimeMillis() - start;
        System.out.println("from cache time: " + dur);
    }
}
