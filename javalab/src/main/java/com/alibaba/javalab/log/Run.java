/**
 * Function: 
 * 
 * File Created at 2010-2-2
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.log;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.javalab.log.biz.p1.Service;

/**
 * TODO Comment of Run
 * 
 * @author li.jinl
 */
public class Run {

    private static final int             THREAD_COUNT   = 100;
    private static final int             REQUEST_COUNT  = 1000;

    private static final ExecutorService THREAD_POOL_P1 = Executors
                                                                .newFixedThreadPool(THREAD_COUNT);
    private static final ExecutorService THREAD_POOL_P2 = Executors
                                                                .newFixedThreadPool(THREAD_COUNT);

    private static final Random          RANDOM         = new Random(1000);

    public static void main(String[] args) {
        runP1();
        runP2();

        THREAD_POOL_P1.shutdown();
        THREAD_POOL_P2.shutdown();
    }

    public static void runP1() {
        P1 p = new P1();
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread t = new Thread(p);
            THREAD_POOL_P1.execute(t);
        }
    }

    public static void runP2() {
        P2 p = new P2();
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread t = new Thread(p);
            THREAD_POOL_P2.execute(t);
        }
    }

    public static class P1 implements Runnable {

        private com.alibaba.javalab.log.biz.p1.Service service = new Service();

        @Override
        public void run() {
            for (int i = 0; i < REQUEST_COUNT; i++) {
                service.logic(String.valueOf(RANDOM.nextInt()));
            }
        }

    }

    public static class P2 implements Runnable {

        private com.alibaba.javalab.log.biz.p2.Service service = new com.alibaba.javalab.log.biz.p2.Service();

        @Override
        public void run() {
            for (int i = 0; i < REQUEST_COUNT; i++) {
                service.logic(String.valueOf(RANDOM.nextInt()));
            }
        }

    }

}
