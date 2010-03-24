/**
 * Function: 
 * 
 * File Created at 2010-3-2
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.btrace.args;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.Return;

/**
 * @author li.jinl
 */
@BTrace
public class ReturnExample {

    @OnMethod(clazz = "com.alibaba.javalab.btrace.Demo", method = "hello", type = "java.lang.String(java.lang.String)", location = @Location(Kind.RETURN))
    public static void traceHelloWithName(@Return String str) {
        BTraceUtils.println(BTraceUtils.str(str));
    }

}
