/**
 * Function: 
 * 
 * File Created at 2010-5-10
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.tool.trace;

import static com.sun.btrace.BTraceUtils.println;
import static com.sun.btrace.BTraceUtils.str;
import static com.sun.btrace.BTraceUtils.strcat;
import static com.sun.btrace.BTraceUtils.timeMillis;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;
import com.sun.btrace.annotations.TLS;

/**
 * @author li.jinl
 */
@BTrace
public class TimeTrace {

    private static final String CLASS  = "/com\\.alibaba\\.myalibaba\\.web\\..*/";
    private static final String METHOD = "/.*/";

    @TLS
    private static long         start;
    @TLS
    private static long         end;

    @OnMethod(clazz = CLASS, method = METHOD, location = @Location(Kind.ENTRY))
    public static void start() {
        start = timeMillis();
    }

    @OnMethod(clazz = CLASS, method = METHOD, location = @Location(Kind.RETURN))
    public static void end(@ProbeClassName String clazz, @ProbeMethodName String method) {
        end = timeMillis();
        println(strcat(strcat(strcat(strcat(clazz, "."), method), ":"), str(end - start)));
    }

}
