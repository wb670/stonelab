/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.javalab.thread;

import java.util.concurrent.CountDownLatch;

/**
 * @author Maurice.J Dec 9, 2009 2:31:24 PM
 */
public class CountDownLatchTest {

    private static final int count = 10;

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(count);
        Worker worker = new Worker(latch);
        for (int i = 0; i < count; i++) {
            Thread thread = new Thread(worker);
            thread.start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println(Thread.currentThread().getName() + ":done");
    }

    private static class Worker implements Runnable {

        private CountDownLatch latch;

        public Worker(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread().getName() + ":done");
            latch.countDown();
        }

    }

}
