/**
 * Function: 
 * 
 * File Created at 2010-5-12
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.btrace.trace;

import static com.sun.btrace.BTraceUtils.currentThread;
import static com.sun.btrace.BTraceUtils.name;
import static com.sun.btrace.BTraceUtils.println;
import static com.sun.btrace.BTraceUtils.str;
import static com.sun.btrace.BTraceUtils.strcat;
import static com.sun.btrace.BTraceUtils.timeMillis;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.TLS;

/**
 * @author li.jinl
 */
@BTrace
public class EasyTimeTracer {

    private static final String CLASS  = "com.alibaba.exodus2.web.member.module.screen.Myalibaba";
    private static final String METHOD = "execute";

    @TLS
    //开始时间
    private static long         start;
    @TLS
    //当前线程名
    private static String       name;

    /**
     * 记录开始时间
     */
    @OnMethod(clazz = CLASS, method = METHOD, location = @Location(Kind.ENTRY))
    public static void start() {
        start = timeMillis();
        name = name(currentThread());
    }

    /**
     * 记录结束时间
     */
    @OnMethod(clazz = CLASS, method = METHOD, location = @Location(Kind.RETURN))
    public static void end() {
        if (name != null) {
            println(strcat(strcat(name, ":"), str(timeMillis() - start)));
        }
    }
}
