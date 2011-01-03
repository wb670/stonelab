/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.learning.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2010-12-17
 */
public class LockExample {

    private static final int FULL_SIZE  = 10;
    private static final int EMPTY_SIZE = 3;

    private ReentrantLock    lock       = new ReentrantLock();
    private Condition        condition  = lock.newCondition();
    private List<String>     products   = new ArrayList<String>(FULL_SIZE);

    public static void main(String[] args) {
        LockExample le = new LockExample();
        le.run();
    }

    public void run() {
        Producer p = new Producer(products);
        Consumer c = new Consumer(products);

        new Thread(p).start();
        new Thread(p).start();
        new Thread(p).start();
        new Thread(p).start();
        new Thread(c).start();

        while (true) {
            if (products.size() > FULL_SIZE || products.size() < EMPTY_SIZE) {
                System.out.println(products);
            }
        }

    }

    public class Producer implements Runnable {

        private List<String> products;

        public Producer(List<String> products){
            this.products = products;
        }

        @Override
        public void run() {
            while (true) {
                lock.lock();
                while (products.size() >= FULL_SIZE) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                products.add("P");
                condition.signalAll();
                lock.unlock();
            }
        }
    }

    public class Consumer implements Runnable {

        private List<String> products;

        public Consumer(List<String> products){
            this.products = products;
        }

        @Override
        public void run() {
            while (true) {
                lock.lock();
                while (products.size() <= EMPTY_SIZE) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                products.remove(0);
                condition.signalAll();
                lock.unlock();
            }
        }

    }

    public void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

}
