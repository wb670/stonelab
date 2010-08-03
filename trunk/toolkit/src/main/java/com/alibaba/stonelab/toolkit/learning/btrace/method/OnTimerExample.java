/**
 * Function: 
 * 
 * File Created at 2010-2-22
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.stonelab.toolkit.learning.btrace.method;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.OnTimer;

/**
 * @author li.jinl
 */
@BTrace
public class OnTimerExample {

    private static int COUNT = 0;

    @OnMethod(clazz = "com.alibaba.javalab.btrace.Demo", method = "greet")
    public static void traceHello() {
        COUNT++;
    }

    @OnTimer(5000)
    public static void statistics() {
        BTraceUtils.println(COUNT);
    }
}
