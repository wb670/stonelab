/**
 * Function: 
 * 
 * File Created at 2010-3-5
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.misc;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;

/**
 * @author li.jinl
 */
public class Test implements Callable<Boolean> {

    private static final String TEMPLATE = "<p>\"session.{0}\" <%=session.{0}()%></p>";

    public static void main(String[] args) throws Exception {
        Test t = new Test();
        ExecutorService pool = Executors.newFixedThreadPool(100);
        List<Callable<Boolean>> list = new ArrayList<Callable<Boolean>>(100);
        for (int i = 0; i < 20; i++) {
            list.add(t);
        }
        pool.invokeAll(list);
        pool.shutdown();
    }

    @Override
    public Boolean call() throws Exception {
        newObject();
        return Boolean.TRUE;
    }

    public static void newInteger() {
        long s = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            new Integer(100);
        }
        long e = System.currentTimeMillis();
        System.out.println(e - s);
    }

    public static void throwException() {
        long s = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            try {
                throw new NumberFormatException("");
            } catch (Exception e1) {
            }
        }
        long e = System.currentTimeMillis();
        System.out.println(e - s);
    }

    public static void newObject() {
        long s = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            try {
                new Integer("");
            } catch (NumberFormatException e1) {
                try {
                    new Integer("");
                } catch (NumberFormatException e2) {
                    new Integer(1000);
                }
            }
        }
        long e = System.currentTimeMillis();
        System.out.println(e - s);
    }

    public static void print() throws Exception {
        InputStream in = new FileInputStream("d:/tmp/无.txt");
        List<String> lines = IOUtils.readLines(in);
        for (String l : lines) {
            System.out.println(MessageFormat.format(TEMPLATE, l));
        }
    }

    public static void session() {
        Method[] methods = HttpSession.class.getMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }
    }
}
