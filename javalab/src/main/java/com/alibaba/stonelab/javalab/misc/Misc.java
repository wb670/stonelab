/*
 * Copyright 1999-2011 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.misc;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-1-26
 */
public class Misc {

    public static void main(String[] args) throws Exception {
        FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
            private int count;

            @Override
            public Integer call() throws Exception {
                Thread.sleep(5000);
                return ++count;
            }
        });

        task.run();

        for (int i = 0; i < 100; i++) {
            System.out.println(task.get());
        }
    }

}
