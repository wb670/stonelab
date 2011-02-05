/*
 * Copyright 1999-2011 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.common.util;

import java.util.concurrent.CountDownLatch;

import junit.framework.TestCase;

/**
 * <pre>
 * JenkinsHash TestCase
 * 
 * Copied from Gray Watson http://256.com/gray/
 * 
 * Change Point:
 * 1. add thread-unsafe test case
 * </pre>
 * 
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-1-26
 */
public class JenkinsHashTest extends TestCase {

    // test jenkins hash
    public void testHashes() {
        JenkinsHash jenkinsHash = new JenkinsHash();
        for (TestData test : tests) {
            long got = jenkinsHash.hash(test.bytes, test.initial);
            if (got != test.expected) {
                fail("Buffer '" + test.str + "' (len " + test.bytes.length + ") with initial " + test.initial
                     + " expected " + test.expected + " but got " + got);
            }
        }
    }

    // test jenkins hash thread unsafe
    public void testThreadUnsafe() {
        int threads = 20;
        JenkinsHash jenkinsHash = new JenkinsHash();
        CountDownLatch latch = new CountDownLatch(threads);

        ThreadUnsafeRunner runner = new ThreadUnsafeRunner(jenkinsHash, latch);
        for (int i = 0; i < threads; i++) {
            new Thread(runner).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            System.out.println(e);
            // ignore
        }
        assertTrue(true);
    }

    // test datas
    private static TestData[] tests = new TestData[] { new TestData("hello", 0L, 3070638494L),
            new TestData("wow", 0L, 627410295L), new TestData(new byte[] { 0 }, 0L, 1843378377L),
            new TestData(new byte[] { (byte) 255, (byte) 128, 64, 1 }, 0L, 3359486273L),
            new TestData("this is 11c", 0L, 3384459500L), new TestData("this is 12ch", 0L, 313177311L),
            new TestData("this is >12ch", 0L, 2321813933L),
            new TestData("this is much large than 12 characters", 0L, 2771373033L),
            new TestData("hello", 3070638494L, 1535955511L), new TestData("wow", 627410295L, 320141986L),
            new TestData(new byte[] { 0 }, 1843378377L, 341630388L),
            new TestData(new byte[] { (byte) 255, (byte) 128, 64, 1 }, 3359486273L, 2916354366L),
            new TestData("this is 11c", 3384459500L, 1497460513L),
            new TestData("this is 12ch", 313177311L, 1671722359L),
            new TestData("this is >12ch", 2321813933L, 4197822112L),
            new TestData("this is much large than 12 characters", 2771373033L, 1338302094L), };

    /**
     * test thread-unsafe runner
     * 
     * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-1-26
     */
    private static class ThreadUnsafeRunner implements Runnable {

        private JenkinsHash    jenkinsHash;
        private boolean        unsafe;
        private CountDownLatch latch;

        public ThreadUnsafeRunner(JenkinsHash jenkinsHash, CountDownLatch latch){
            this.jenkinsHash = jenkinsHash;
            this.latch = latch;
        }

        @Override
        public void run() {
            while (!unsafe) {
                for (TestData test : tests) {
                    long got = jenkinsHash.hash(test.bytes, test.initial);
                    if (got != test.expected) {
                        unsafe = true;
                    }
                }
            }
            latch.countDown();
        }

    }

    /**
     * Test data to validate the hash.
     * 
     * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-1-26
     */
    private static class TestData {

        public String str;
        public byte[] bytes;
        public long   initial;
        public long   expected;

        public TestData(byte[] bytes, long initial, long expected){
            this.str = "byte[] { ";
            for (byte one : bytes) {
                this.str += one + ", ";
            }
            this.str += "}";
            this.bytes = bytes;
            this.initial = initial;
            this.expected = expected;
        }

        public TestData(String str, long initial, long expected){
            this.str = str;
            this.bytes = new byte[str.length()];
            for (int charCount = 0; charCount < str.length(); charCount++) {
                this.bytes[charCount] = (byte) str.codePointAt(charCount);
            }
            this.initial = initial;
            this.expected = expected;
        }
    }
}
