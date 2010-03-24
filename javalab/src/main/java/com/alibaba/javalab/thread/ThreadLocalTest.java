/**
 * Function: 
 * 
 * File Created at 2010-2-21
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.thread;

/**
 * @author li.jinl
 */
public class ThreadLocalTest {

    private static final int           COUNT   = 10000;

    private static ThreadLocal<String> context = new ThreadLocal<String>();

    public static void main(String[] args) {
        Runnable run = new Runner();

        while (true) {
            for (int i = 0; i < COUNT; i++) {
                Thread t = new Thread(run);
                t.start();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }

    }

    public static class Runner implements Runnable {

        @Override
        public void run() {
            context.set(Thread.currentThread().getName());
        }

    }

}
