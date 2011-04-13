/*
 * Copyright 1999-2011 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.misc;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-4-7
 */
public class Misc2 {

    private static final DelayQueue<Timeout> TIMEOUT_QUEUE = new DelayQueue<Timeout>();

    public static void main(String[] args) throws Exception {
        new Monitor().start(); // 超时监控线程

        new Request(4).start();// 模拟第一个下载
        new Request(3).start();// 模拟第二个下载
        new Request(2).start();// 模拟第三个下载
    }

    /**
     * 模拟一次HttpClient请求
     * 
     * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-4-9
     */
    public static class Request extends Thread {

        private long delay;

        public Request(long delay){
            this.delay = delay;
        }

        public void run() {
            HttpClient hc = new HttpClient();
            GetMethod req = new GetMethod("http://www.python.org/ftp/python/2.7.1/Python-2.7.1.tgz");
            try {
                TIMEOUT_QUEUE.offer(new Timeout(delay * 1000, hc.getHttpConnectionManager()));
                hc.executeMethod(req);
            } catch (Exception e) {
                System.out.println(e);
            }
            req.releaseConnection();
        }

    }

    /**
     * 监工：监控线程，通过DelayQueue，阻塞得到最近超时的对象，强制关闭
     * 
     * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-4-9
     */
    public static class Monitor extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    Timeout timeout = TIMEOUT_QUEUE.take();
                    timeout.forceClose();
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        }

    }

    /**
     * 使用delay queue，对Delayed接口的实现 根据请求当前时间+该请求允许timeout时间，和当前时间比较，判断是否已经超时
     * 
     * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-4-9
     */
    public static class Timeout implements Delayed {

        private long                  debut;
        private long                  delay;
        private HttpConnectionManager manager;

        public Timeout(long delay, HttpConnectionManager manager){
            this.debut = System.currentTimeMillis();
            this.delay = delay;
            this.manager = manager;
        }

        public void forceClose() {
            System.out.println(this.debut + ":" + this.delay);
            if (manager instanceof SimpleHttpConnectionManager) {
                ((SimpleHttpConnectionManager) manager).shutdown();
            }
            if (manager instanceof MultiThreadedHttpConnectionManager) {
                ((MultiThreadedHttpConnectionManager) manager).shutdown();
            }
        }

        @Override
        public int compareTo(Delayed o) {
            if (o instanceof Timeout) {
                Timeout timeout = (Timeout) o;
                if (this.debut + this.delay == timeout.debut + timeout.delay) {
                    return 0;
                } else if (this.debut + this.delay > timeout.debut + timeout.delay) {
                    return 1;
                } else {
                    return -1;
                }
            }
            return 0;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return debut + delay - System.currentTimeMillis();
        }

    }

}
