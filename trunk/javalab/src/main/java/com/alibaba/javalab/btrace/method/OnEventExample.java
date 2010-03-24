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
import com.sun.btrace.annotations.OnEvent;

/**
 * @author li.jinl
 */
@BTrace
public class OnEventExample {

    @OnEvent(value = "jstack")
    public static void jstack() {
        BTraceUtils.jstackAll();
    }

}
