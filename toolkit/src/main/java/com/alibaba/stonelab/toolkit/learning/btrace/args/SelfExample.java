/**
 * Function: 
 * 
 * File Created at 2010-3-2
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.stonelab.toolkit.learning.btrace.args;

import com.alibaba.stonelab.toolkit.learning.btrace.Demo;
import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.Self;

/**
 * @author li.jinl
 */
@BTrace
public class SelfExample {

    @OnMethod(clazz = "com.alibaba.javalab.btrace.Demo", method = "hello", type = "java.lang.String(java.lang.String)")
    public static void traceHelloWithName(@Self Demo demo) {
        BTraceUtils.println(BTraceUtils.str(demo));
    }

}
