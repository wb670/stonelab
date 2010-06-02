/**
 * Function: 
 * 
 * File Created at 2010-5-19
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.misc;

/**
 * @author li.jinl
 */
public class Run {

    private DemoService demoService = new DefaultDemoService();

    public static void main(String[] args) {
        Run run = new Run();
        for (int i = 0; i < 10000; i++) {
            run.run();
        }
    }

    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        demoService.hello("Stone");
    }

}
