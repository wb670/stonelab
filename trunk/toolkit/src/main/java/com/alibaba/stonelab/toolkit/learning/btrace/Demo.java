/**
 * Function: 
 * 
 * File Created at 2010-2-22
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.stonelab.toolkit.learning.btrace;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author li.jinl
 */
public class Demo {

    private static final String       GREET_VALUE = "hello:";
    private static final int          MAX_RANGE   = 10000;

    private static final Demo         DEMO        = new Demo();
    private static final Random       RANDOM      = new Random();
    private static final List<String> LIST        = new ArrayList<String>();

    public static void main(String[] args) {

        while (true) {
            try {
                DEMO.hello(String.valueOf(RANDOM.nextInt(MAX_RANGE)));
                DEMO.hello();
                DEMO.greet();

                LIST.add(String.valueOf(RANDOM.nextInt(MAX_RANGE)));
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
    }

    public String hello(String name) {
        String value = GREET_VALUE + name;
        System.out.println(value);
        return value;
    }

    public int hello() {
        int value = RANDOM.nextInt(MAX_RANGE);
        System.out.println(value);
        return value;
    }

    public void greet() throws RuntimeException {
        if (RANDOM.nextInt() < MAX_RANGE / 2) {
            throw new RuntimeException("RuntimeException");
        }
        System.out.println("hello");
    }

    public static class MemoryPool {

        public static void main(String[] args) {
            MemoryPool pool = new MemoryPool();
            pool.printMemoryPoolNames();
        }

        public void printMemoryPoolNames() {
            List<MemoryPoolMXBean> list = ManagementFactory.getMemoryPoolMXBeans();
            for (MemoryPoolMXBean bean : list) {
                System.out.println(bean.getName());
            }
        }

    }

}
