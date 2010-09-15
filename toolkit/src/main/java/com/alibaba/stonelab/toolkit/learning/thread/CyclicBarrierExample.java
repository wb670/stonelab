/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.learning.thread;

import java.util.concurrent.CyclicBarrier;

/**
 * @author Stone.J 2010-9-14 上午11:25:07
 */
public class CyclicBarrierExample {

    private static final int WORKER = 10;

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(WORKER, new Runnable() {

            @Override
            public void run() {
                System.out.println("OK.It's my turn now.");
            }
        });

        Worker worker = new Worker(5, barrier);
        for (int i = 0; i < WORKER; i++) {
            new Thread(worker).start();
        }

    }

    public static class Worker implements Runnable {

        private int           time;
        private CyclicBarrier barrier;

        public Worker(int time, CyclicBarrier barrier){
            this.time = time;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            pause(time);
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void pause(int time) {
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
