/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.learning.thread;

import java.util.concurrent.Semaphore;

/**
 * 类SemaphoreExample.java的实现描述：TODO 类实现描述
 * 
 * @author Stone.J 2010-9-7 下午04:11:17
 */
public class SemaphoreExample {

    private static final int            POOL_SIZE = 10;
    private static final ConnectionPool pool      = new ConnectionPool();

    public static void main(String[] args) {
        Statistics s = new Statistics();
        Runner r = new Runner();

        new Thread(s).start();

        while (true) {
            new Thread(r).start();
            pause(500);
        }
    }

    private static void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    private static class Statistics implements Runnable {

        @Override
        public void run() {
            while (true) {
                System.out.println(pool.getUsed());
                pause(500);
            }
        }

    }

    private static class Runner implements Runnable {

        @Override
        public void run() {
            pool.getConnection();
            pause(10000);
            pool.returnConnection();
        }

    }

    private static class ConnectionPool {

        private Semaphore semaphore = new Semaphore(POOL_SIZE);
        private int       used      = 0;

        public Object getConnection() {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
            }
            used++;
            System.out.println("get");
            return new Object();
        }

        public void returnConnection() {
            used--;
            System.out.println("return");
            semaphore.release();
        }

        public int getUsed() {
            return used;
        }

    }

}
