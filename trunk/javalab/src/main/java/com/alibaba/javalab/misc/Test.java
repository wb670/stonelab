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
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;

/**
 * @author li.jinl
 */
public class Test {

    private static final String TEMPLATE = "<p>\"session.{0}\" <%=session.{0}()%></p>";

    public static void main(String[] args) throws Exception {
        session();
        print();
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
