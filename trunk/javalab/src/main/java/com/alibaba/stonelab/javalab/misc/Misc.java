/*
 * Copyright 1999-2011 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.misc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-1-26
 */
public class Misc {

    public static void main(String[] args) {
        Executor ret = (Executor) Proxy.newProxyInstance(Executor.class.getClassLoader(),
                                                         new Class<?>[] { SimpleExecutor.class },
                                                         new TimerInterceptor(new SimpleExecutor()));
        System.out.println(ret.exe());
    }

    public static class SimpleExecutor implements Executor {

        @Override
        public int exe() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        }

    }

    public static interface Executor {

        public int exe();
    }

    public static class TimerInterceptor implements InvocationHandler {

        private Object real;

        public TimerInterceptor(Object real){
            this.real = real;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            long start = System.currentTimeMillis();
            Object ret = method.invoke(real, args);
            System.out.println("Method:" + method.getName() + ":" + (System.currentTimeMillis() - start));
            return ret;
        }

    }

}
