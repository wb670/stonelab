/*
 * Copyright 1999-2011 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.aliinc.seo;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Random;

import com.alibaba.stonelab.javalab.common.http.HttpClient;
import com.alibaba.stonelab.javalab.common.http.Response;
import com.alibaba.stonelab.javalab.common.util.JenkinsHash;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-1-24
 */
public class SeoEvaluator {

    private static final String URL_ALEXA    = "http://data.alexa.com/data/?cli=10&dat=s&url={0}";
    private static final String URL_WHOIS    = "http://www.whois.net/whois/{0}";
    private static final String URL_WHOIS2   = "http://whois.hichina.com/cgi-bin/whois?domain={0}";
    private static final String URL_PAGERANK = "http://toolbarqueries.google.com/search?client=navclient-auto&hl=en&ch=6{0}&ie=UTF-8&oe=UTF-8&features=Rank&q=info:{1}";

    private HttpClient          httpClient   = new HttpClient();
    {
        httpClient.getHeaders().put("User-Agent",
                                    "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.3) Gecko/20100401");
    }

    private Logger              logger;
    private String              type;

    public static void main(String[] args) throws Exception {
        args = new String[] { "/home/stone/tmp/seo.log", "Alexa", "1" };
        final SeoEvaluator seo = new SeoEvaluator(args[0], args[1]);
        Runner runner = new Runner(seo);
        int threads = Integer.valueOf(args[2]);

        // start
        for (int i = 0; i < threads; i++) {
            new Thread(runner).start();
        }
    }

    public SeoEvaluator(String type){
        this.type = type;
    }

    public SeoEvaluator(String file, String type){
        this.logger = new Logger(file, "UTF-8");
        this.type = type;
    }

    public Response evaluateAlexa(String domain) {
        String url = MessageFormat.format(URL_ALEXA, domain);
        Response res = null;
        try {
            long start = System.currentTimeMillis();
            res = httpClient.get(url);
            long dur = System.currentTimeMillis() - start;
            if (res.getCode() == 200) {
                successLog("Alexa", "Success", dur);
            } else {
                failLog("Alexa", "Fail", String.valueOf(res.getCode()));
            }
        } catch (Exception e) {
            failLog("Alexa", "Fail", e.getMessage());
        }
        return res;
    }

    public Response evaluatePageRank(String domain) {
        String ch = String.valueOf(new JenkinsHash().hash(("info:" + domain).getBytes(), 0x0E6359A60L));
        String url = MessageFormat.format(URL_PAGERANK, ch, domain);
        Response res = null;
        try {
            long start = System.currentTimeMillis();
            res = httpClient.get(url);
            long dur = System.currentTimeMillis() - start;
            if (res.getCode() == 200) {
                successLog("PageRank", "Success", dur);
            } else {
                failLog("PageRank", "Fail", String.valueOf(res.getCode()));
            }
        } catch (Exception e) {
            failLog("PageRank", "Fail", e.getMessage());
        }
        return res;
    }

    public Response evaluateWhois(String domain) {
        String url = MessageFormat.format(URL_WHOIS, domain);
        Response res = null;
        try {
            long start = System.currentTimeMillis();
            res = httpClient.get(url);
            long dur = System.currentTimeMillis() - start;
            if (res.getCode() == 200) {
                successLog("Whois", "Success", dur);
            } else {
                failLog("Whois", "Fail", String.valueOf(res.getCode()));
            }
        } catch (Exception e) {
            failLog("Whois", "Fail", e.getMessage());
        }
        return res;
    }

    public Response evaluateWhois2(String domain) {
        String url = MessageFormat.format(URL_WHOIS2, domain);
        Response res = null;
        try {
            long start = System.currentTimeMillis();
            res = httpClient.get(url);
            long dur = System.currentTimeMillis() - start;
            if (res.getCode() == 200) {
                successLog("Whois2", "Success", dur);
            } else {
                failLog("Whois2", "Fail", String.valueOf(res.getCode()));
            }
        } catch (Exception e) {
            failLog("Whois2", "Fail", e.getMessage());
        }
        return res;
    }

    private void failLog(String type, String status, String reason) {
        if (this.logger == null) {
            return;
        }
        if (!this.type.equalsIgnoreCase(type)) {
            return;
        }
        logger.info(type + "  " + status + "  " + reason);
    }

    private void successLog(String type, String status, long dur) {
        if (this.logger == null) {
            return;
        }
        if (!this.type.equalsIgnoreCase(type)) {
            return;
        }
        logger.info(type + "  " + status + "  " + dur);
    }

    public static class Runner implements Runnable {

        private static final String[] DOMAIN_POOL = { "sina.com", "javaeye.com", "blogjava.net" };
        private static final Random   random      = new Random();

        private SeoEvaluator          seo;
        private String                type;

        public Runner(SeoEvaluator seo){
            this.seo = seo;
            this.type = seo.type;
        }

        @Override
        public void run() {
            while (true) {
                String domain = DOMAIN_POOL[random.nextInt(DOMAIN_POOL.length)];
                if ("Alexa".equals(type)) {
                    seo.evaluateAlexa(domain);
                }
                if ("PageRank".equals(type)) {
                    seo.evaluatePageRank(domain);
                }
                if ("Whois".equals(type)) {
                    seo.evaluateWhois(domain);
                }
                if ("Whois2".equals(type)) {
                    seo.evaluateWhois2(domain);
                }
                try {
                    Thread.sleep(random.nextInt(3000));
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public static class Logger {

        private BufferedWriter writer;

        public Logger(String file, String encoding){
            try {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoding));
            } catch (Exception e) {
                throw new RuntimeException("logger init error.", e);
            }
        }

        public void info(String msg) {
            try {
                writer.write(new Date() + ":" + msg + "\r\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void close() {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
