/**
 * Function: 
 * 
 * File Created at 2010-2-22
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.btrace.method;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;

/**
 * @author li.jinl
 */
@BTrace
public class OnMethodExample {

    @OnMethod(clazz = "com.alibaba.javalab.btrace.Demo", method = "hello", type = "java.lang.String(java.lang.String)")
    public static void traceHelloWithName() {
        BTraceUtils.println("Demo.Hello(String) called.");
    }

    @OnMethod(clazz = "com.alibaba.javalab.btrace.Demo", method = "hello", type = "int()")
    public static void traceHello() {
        BTraceUtils.println("Demo.Hello() called.");
    }

    @OnMethod(clazz = "com.alibaba.javalab.btrace.Demo", method = "greet", location = @Location(value = Kind.THROW))
    public static void traceGreet() {
        BTraceUtils.println("Demo.Greet() throws RumtimeException.");
    }
}
