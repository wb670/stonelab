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
 * 
 * 单线程下:
 * from spring time: 1750 => 0.0001750
 * from cache time:   187 => 0.0000187
 * 1750 / 187 = 9.4(倍)
 * 
 * 同比创建对象的测试
 * new java object:  1078 => 0.0001078
 * 
 * 多线程下:(5个线程)
 * from spring time: 10515 => 0.0010515
 * from cache time:  781   => 0.0000781
 * 10510 / 781 => 13(倍)
 * </pre>
 * 
 * @author Stone.J 2010-8-3 下午06:11:31
 */
public class SpringSingleBeanGetTest {

    private static final String              LOCATION  = "classpath:learning/spring/bean.xml";
    private static final ApplicationContext  CTX       = new ClassPathXmlApplicationContext(LOCATION);
    private static final String              BEAN_NAME = "testBean";
    private static final Map<String, Object> CACHE     = new HashMap<String, Object>();

    static {
        CACHE.put(BEAN_NAME, CTX.getBean(BEAN_NAME));
    }

    private static final int                 LOOP      = 10000000;

    public static void main(String[] args) throws Exception {
        testNewObject();
        //        Runner s = new Runner(true);
        //        Runner c = new Runner(false);
        //
        //        for (int i = 0; i < 5; i++) {
        //            new Thread(s).start();
        //        }
        //        for (int i = 0; i < 5; i++) {
        //            new Thread(c).start();
        //        }

    }

    public static class Runner implements Runnable {

        private boolean flag;

        public Runner(boolean flag) {
            this.flag = flag;
        }

        @Override
        public void run() {
            if (flag) {
                testFromSpring();
            } else {
                testFromCache();
            }
        }

    }

    public static void testNewObject() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < LOOP; i++) {
            new HashMap<String, String>(32);
        }
        long dur = System.currentTimeMillis() - start;
        System.out.println("new java object: " + dur);
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
