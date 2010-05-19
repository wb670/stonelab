/**
 * Function: 
 * 
 * File Created at 2010-5-13
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.btrace.trace;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnTimer;

/**
 * @author li.jinl
 */
@BTrace
public class InfoTracer {

    @OnTimer(5000)
    public static void info() {
        BTraceUtils.jstackAll();
        BTraceUtils.println(BTraceUtils.heapUsage());
        BTraceUtils.dumpHeap("Tenured Gen");
    }

}
