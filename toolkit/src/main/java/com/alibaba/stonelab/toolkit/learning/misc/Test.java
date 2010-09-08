/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.learning.misc;

/**
 * @author Stone.J 2010-8-31 上午09:57:39
 */
public class Test extends Thread {

    public static void main(String[] args) {
        new Test().start();
    }

    public void run() {
        while (true) {
            Thread[] x = new Thread[100];
            Thread.enumerate(x);
            for (int i = 0; i < 100; i++) {
                Thread t = x[i];
                if (t == null) break;
                else System.out.println(t.getName() + "\t" + t.getPriority() + "\t" + t.isAlive() + "\t" + t.isDaemon());
            }
        }
    }
}
