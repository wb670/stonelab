/**
 * Function: 
 * 
 * File Created at 2010-3-30
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.stonelab.toolkit.learning.btrace.args;

import static com.sun.btrace.BTraceUtils.str;
import static com.sun.btrace.BTraceUtils.strcat;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.TargetInstance;
import com.sun.btrace.annotations.TargetMethodOrField;

/**
 * @author li.jinl
 */
@BTrace
public class TargetExample {

    @OnMethod(clazz = "com.alibaba.javalab.btrace.Demo", method = "hello", location = @Location(value = Kind.CALL, clazz = "/.*/", method = "/.*/"))
    public static void traceHelloWithName(@TargetInstance Object instance, @TargetMethodOrField String name) {
        str(strcat(strcat(str(instance), ":"), name));
    }
}
