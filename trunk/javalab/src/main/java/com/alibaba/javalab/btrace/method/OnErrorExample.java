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

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnError;

/**
 * @author li.jinl
 */
@BTrace
public class OnErrorExample {

    @OnError
    public static void onError() {
        //TODO:how to use this annotation?
    }

}
