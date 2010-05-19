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

import static com.sun.btrace.BTraceUtils.println;
import static com.sun.btrace.BTraceUtils.str;
import static com.sun.btrace.BTraceUtils.strcat;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.Return;
import com.sun.btrace.annotations.Self;

/**
 * @author li.jinl
 */
@BTrace
public class ParamTracer {

    private static final String CLASS  = "com.alibaba.exodus2.biz.member.bo.TrustPassBO";
    private static final String METHOD = "isTrustPass";

    @OnMethod(clazz = CLASS, method = METHOD, location = @Location(Kind.RETURN))
    public static void trace(@Self Object self, String memberLevel, @Return boolean isTP) {
        println(strcat(strcat(memberLevel, ":"), str(isTP)));
    }

}
