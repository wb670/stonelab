package com.alibaba.javalab.btrace.trace;

/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */

import static com.sun.btrace.BTraceUtils.print;
import static com.sun.btrace.BTraceUtils.printArray;
import static com.sun.btrace.BTraceUtils.println;

import java.lang.reflect.Method;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.Return;
import com.sun.btrace.annotations.Self;

/**
 * Dubbo调用远程方法的监控 监控信息: <li>类.方法</li> <li>参数</li> <li>返回值</li>
 * 
 * @author li.jinl 2010-5-20 上午10:08:38
 */
@BTrace
public class DubboTracer {

    @OnMethod(clazz = "/com\\.alibaba\\.dubbo\\.rpc\\..*RpcInvoker/", method = "invoke", location = @Location(Kind.RETURN))
    public static void invoker(@Self Object self, Object proxy, Method method, Object[] args, @Return Object ret) {
        println("=========================================");
        print("Method:");
        println(method);
        print("Args:");
        printArray(args);
        print("Return:");
        println(ret);
        println("=========================================");
    }

    @OnMethod(clazz = "/com\\.alibaba\\.dubbo\\.rpc\\..*RpcInvoker/", method = "invoke", location = @Location(Kind.RETURN))
    public static void invoker(@Self Object self, Object proxy, Method method, Object[] args, long timeout,
                               @Return Object ret) {
        println("=========================================");
        print("Method:");
        println(method);
        print("Args:");
        printArray(args);
        print("Return:");
        println(ret);
        println("=========================================");
    }

}
