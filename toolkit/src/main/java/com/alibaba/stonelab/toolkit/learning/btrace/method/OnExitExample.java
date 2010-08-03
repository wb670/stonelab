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
import com.sun.btrace.annotations.OnExit;
import com.sun.btrace.annotations.OnTimer;

/**
 * @author li.jinl
 */
@BTrace
public class OnExitExample {

    private static int COUNT = 0;

    @OnExit
    public static void onExit(int code) {
        String value = BTraceUtils.strcat("Exit:", BTraceUtils.str(code));
        BTraceUtils.print(value);
    }

    @OnTimer(1000)
    public static void onTimer() {
        COUNT++;
        if (COUNT > 10) {
            BTraceUtils.exit(-1);
        }
    }

}
