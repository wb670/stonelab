/**
 * Function: 
 * 
 * File Created at 2010-2-26
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.stonelab.toolkit.learning.btrace.method;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnProbe;

/**
 * @author li.jinl
 */
@BTrace
public class OnProbeExample {

    @OnProbe(namespace = "on.probe.example", name = "hello")
    public static void traceHello() {
        BTraceUtils.println("hello");
    }

    @OnProbe(namespace = "on.probe.example", name = "greet")
    public static void traceGreet() {
        BTraceUtils.println("greet");
    }
}
