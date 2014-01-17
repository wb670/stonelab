/*
 * Copyright 2013 Aliyun.com All right reserved. This software is the
 * confidential and proprietary information of Aliyun.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Aliyun.com .
 */
package com.alibaba.stonelab.javalab.oss;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.aliyun.openservices.HttpMethod;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.openservices.oss.model.ObjectMetadata;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">li.jinl</a> Dec 25, 2013
 */
public class OssTest {

    private static final String ACCESS_ID     = "t3epvbWynK6EsP19";
    private static final String ACCESS_KEY    = "E9QU0JrFIRhgXCY119nyl2eV8z488M";
    private static final String ENDPOINT      = "http://oss.aliyuncs.com";
    private static final String BUCKET_PREFIX = "ace-bucket-2-";
    //private static final String FILE_PREFIX   = "/Users/stone/Tmp/ace/";
    private static final String FILE_PREFIX   = "/home/li.jinl/ace/";
    private static final int    BUCKETS_SIZE  = 10;

    public static void main(String[] args) throws Exception {
        OssTest t = new OssTest();
        t.test();

        if (args.length != 3) {
            System.out.println("illegal args. OssTest Mode Concurrency.");
            System.exit(-1);
        }
        String mode = args[0].trim();
        int concurrency = Integer.valueOf(args[1].trim());
        int loop = Integer.valueOf(args[2].trim());
        String file = args[3].trim();

        System.out.println("commands: OssTest " + mode + " " + concurrency);
        System.out.println("===========================================================");
        if (mode.equalsIgnoreCase("s")) {
            t.testSingleBucket(concurrency, loop, file);
        } else if (mode.equalsIgnoreCase("m")) {
            t.testMultiBucket(concurrency, loop, file);
        }
        System.out.println();
    }

    public void test() {
        OSSClient client = createOssClient();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, 10);
        Date d = cal.getTime();
        //d.setTime(1388131613 * 1000L);
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(BUCKET_PREFIX + "0",
                "10089/6dac807b-af0d-435c-bf73-422d6ddfba15/upload.war");
        req.setExpiration(d);
        req.setMethod(HttpMethod.PUT);
        //req.addUserMetadata("leo", "application/zip");
        //req.addUserMetadata("Content-Type", "application/zip");
        System.out.println(client.generatePresignedUrl(req));
        System.exit(0);
    }

    public void init() {
        OSSClient client = createOssClient();
        for (int i = 0; i < 10; i++) {
            client.createBucket(BUCKET_PREFIX + i);
        }
    }

    public void testSingleBucket(int concurrency, int loop, String file) throws Exception {
        List<UploadWorker> workers = new ArrayList<UploadWorker>(concurrency);
        CountDownLatch latch = new CountDownLatch(concurrency);
        for (int i = 0; i < concurrency; i++) {
            workers.add(new UploadWorker(BUCKET_PREFIX + "0", FILE_PREFIX + file + (i + 1) + ".war", latch, loop));
        }
        for (UploadWorker uploadWorker : workers) {
            new Thread(uploadWorker).start();
        }

        latch.await();
        statistic(workers);
    }

    public void testMultiBucket(int concurrency, int loop, String file) throws Exception {
        List<UploadWorker> workers = new ArrayList<UploadWorker>(concurrency);
        CountDownLatch latch = new CountDownLatch(concurrency);
        for (int i = 0; i < concurrency; i++) {
            workers.add(new UploadWorker(BUCKET_PREFIX + (i % BUCKETS_SIZE), FILE_PREFIX + file + (i + 1) + ".war",
                    latch, loop));
        }
        for (UploadWorker uploadWorker : workers) {
            new Thread(uploadWorker).start();
        }

        latch.await();
        statistic(workers);
    }

    protected OSSClient createOssClient() {
        return new OSSClient(ENDPOINT, ACCESS_ID, ACCESS_KEY);
    }

    protected void statistic(List<UploadWorker> workers) {
        int count = 0;
        long dur = 0;
        long length = 0;
        for (UploadWorker uploadWorker : workers) {
            if (!uploadWorker.isSuccess()) {
                continue;
            }
            count++;
            dur += uploadWorker.getDur();
            length += uploadWorker.getLength();
        }
        System.out.println(MessageFormat.format("C={0};\tS={1};\tD={2};\tS={3};\tB={4}", workers.size(), count, dur
                / count, length / dur, length / dur * count));
    }

    protected class UploadWorker implements Runnable {

        private String         bucket;
        private String         file;
        private CountDownLatch latch;
        private int            loop;

        private long           length;
        private long           dur;    // millseconds
        private boolean        success;

        public UploadWorker(String bucket, String file, CountDownLatch latch, int loop) {
            super();
            this.bucket = bucket;
            this.file = file;
            this.latch = latch;
            this.loop = loop;
        }

        @Override
        public void run() {
            for (int i = 0; i < loop; i++) {
                try {
                    OSSClient client = createOssClient();
                    File f = new File(file);
                    InputStream in = new FileInputStream(f);
                    ObjectMetadata meta = new ObjectMetadata();
                    meta.setContentLength(f.length());
                    long start = System.currentTimeMillis();
                    client.putObject(bucket, f.getName(), in, meta);
                    long dur = System.currentTimeMillis() - start;

                    in.close();
                    success = true;
                    this.dur += dur;
                    this.length += f.length();
                } catch (Exception e) {
                    System.out.println(e);
                    success = false;
                }
            }
            latch.countDown();
        }

        public long getDur() {
            return dur;
        }

        public long getLength() {
            return length;
        }

        public boolean isSuccess() {
            return success;
        }
    }

}
