/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.learning.misc;

import java.util.concurrent.Semaphore;

/**
 * @author Stone.J 2010-8-31 上午09:57:39
 */
public class Test extends Thread {

    public static void main(String[] args) throws Exception {
        Semaphore latch = new Semaphore(0);
        int ret;
        ret = latch.drainPermits();
        System.out.println(ret + ":" + latch.tryAcquire());
        ret = latch.drainPermits();
        System.out.println(ret + ":" + latch.tryAcquire());
        ret = latch.drainPermits();
        System.out.println(ret + ":" + latch.tryAcquire());
    }
}
