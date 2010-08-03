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

import java.lang.management.MemoryUsage;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnLowMemory;

/**
 * @author li.jinl
 */
//TODO:Eden Space,Survivor Survivor有作用吗?
@BTrace
public class OnLowMemoryExample {

    @OnLowMemory(pool = "Eden Space", threshold = 5 * 1000 * 1000)
    public static void traceEdenSpace(MemoryUsage mu) {
        String value = BTraceUtils.strcat("Eden Space:\n", BTraceUtils.str(mu));
        BTraceUtils.println(value);
    }

    @OnLowMemory(pool = "Survivor Space", threshold = 3 * 100 * 1000)
    public static void traceEdenSpace2(MemoryUsage mu) {
        String value = BTraceUtils.strcat("Survivor Space:\n", BTraceUtils.str(mu));
        BTraceUtils.println(value);
    }

    @OnLowMemory(pool = "Tenured Gen", threshold = 10 * 1000 * 1000)
    public static void traceTenuredGen(MemoryUsage mu) {
        String value = BTraceUtils.strcat("Tenured Gen:\n", BTraceUtils.str(mu));
        BTraceUtils.println(value);
    }

    @OnLowMemory(pool = "Perm Gen", threshold = 3 * 1000 * 1000)
    public static void tracePermGen(MemoryUsage mu) {
        String value = BTraceUtils.strcat("Perm Gen:\n", BTraceUtils.str(mu));
        BTraceUtils.println(value);
    }

}
