/**
 * Function: 
 * 
 * File Created at 2010-5-19
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.misc;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.Self;

/**
 * @author li.jinl
 */
@BTrace
public class DemoTracer {

    @OnMethod(clazz = "com.alibaba.javalab.misc.DemoService", method = "hello")
    public static void onHello(@Self Object self, String name) {
        BTraceUtils.println(name);
    }

}
