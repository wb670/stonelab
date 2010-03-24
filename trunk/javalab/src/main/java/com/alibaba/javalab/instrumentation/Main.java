/**
 * Function: 
 * 
 * File Created at 2010-3-14
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.instrumentation;

import java.text.MessageFormat;

/**
 * @author li.jinl
 */
public class Main {

    private static final String TEMPLATE = "PER={0}::NEW={1}";
    private static final Model  m        = new Model();

    public static void main(String[] args) throws Exception {
        while (true) {
            print(MessageFormat.format(TEMPLATE, m.getId(), new Model().getId()));
            sleep(1);
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
