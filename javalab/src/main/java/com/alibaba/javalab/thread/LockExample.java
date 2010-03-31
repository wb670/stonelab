/**
 * Function: 
 * 
 * File Created at 2010-3-31
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <pre>
 *  CLassLock:142577
 *  AtomLock:  91890
 * </pre>
 * 
 * @author li.jinl
 */
public class LockExample {

    private static final int               COUNT  = 100;

    private static final Map<String, Test> config = new HashMap<String, Test>(2);

    static {
        config.put("ClassLock", new CLassLock());
        config.put("AtomLock", new AtomLock());
    }

    public static void main(String[] args) throws Exception {
        String name = args[0];
        LockExample.test(config.get(name));
    }

    public static void test(Test test) throws Exception {
        ExecutorService pool1 = Executors.newFixedThreadPool(COUNT);
        ExecutorService pool2 = Executors.newFixedThreadPool(COUNT);
        ExecutorService pool3 = Executors.newFixedThreadPool(COUNT);

        List<Callable<Boolean>> list1 = new ArrayList<Callable<Boolean>>(COUNT);
        List<Callable<Boolean>> list2 = new ArrayList<Callable<Boolean>>(COUNT);
        List<Callable<Boolean>> list3 = new ArrayList<Callable<Boolean>>(COUNT);

        CountDownLatch latch1 = new CountDownLatch(COUNT);
        CountDownLatch latch2 = new CountDownLatch(COUNT);
        CountDownLatch latch3 = new CountDownLatch(COUNT);

        LockCall call1 = new LockCall(latch1, test, 1);
        LockCall call2 = new LockCall(latch2, test, 2);
        LockCall call3 = new LockCall(latch3, test, 3);

        for (int i = 0; i < COUNT; i++) {
            list1.add(call1);
            list2.add(call2);
            list3.add(call3);
        }

        long start = System.currentTimeMillis();
        pool1.invokeAll(list1);
        pool2.invokeAll(list2);
        pool3.invokeAll(list3);

        latch1.await();
        latch2.await();
        latch3.await();

        long end = System.currentTimeMillis();
        System.out.println(test.getClass().getSimpleName() + ":" + (end - start));

        pool1.shutdown();
        pool2.shutdown();
        pool3.shutdown();
    }

    public static class LockCall implements Callable<Boolean> {

        private CountDownLatch latch;
        private Test           test;
        private int            method;

        public LockCall(CountDownLatch latch, Test test, int method) {
            this.latch = latch;
            this.test = test;
            this.method = method;
        }

        public Boolean call() throws Exception {
            try {
                switch (method) {
                    case 1:
                        test.method1();
                    case 2:
                        test.method2();
                    case 3:
                        test.method3();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            latch.countDown();
            return true;
        }

    }

    /**
     * 对象粒度锁
     */
    public static class CLassLock implements Test {

        public void method1() throws Exception {
            synchronized (this) {
                Thread.sleep(10);
            }
        }

        public void method2() throws Exception {
            synchronized (this) {
                Thread.sleep(20);
            }
        }

        public void method3() throws Exception {
            synchronized (this) {
                Thread.sleep(100);
            }
        }

    }

    /**
     * 细粒度锁实现
     */
    public static class AtomLock implements Test {

        private Object lock1 = new Object();
        private Object lock2 = new Object();
        private Object lock3 = new Object();

        public void method1() throws Exception {
            synchronized (lock1) {
                Thread.sleep(10);
            }
        }

        public void method2() throws Exception {
            synchronized (lock2) {
                Thread.sleep(20);
            }
        }

        public void method3() throws Exception {
            synchronized (lock3) {
                Thread.sleep(100);
            }
        }

    }

    /**
     * 测试接口
     */
    public static interface Test {

        public void method1() throws Exception;

        public void method2() throws Exception;

        public void method3() throws Exception;

    }

}
