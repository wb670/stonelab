/**
 * Function: 
 * 
 * File Created at 2010-3-14
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.stonelab.toolkit.learning.instrumentation;

import java.text.MessageFormat;

/**
 * @author li.jinl
 */
public class Main {

    private static final String TEMPLATE = "Model ID:{0}";

    public static void main(String[] args) throws Exception {
        while (true) {
            print(MessageFormat.format(TEMPLATE, new Model().getId()));
            sleep(5);
        }
    }

    private static void print(String content) {
        System.out.println(content);
    }

    private static void sleep(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
        }
    }

}
