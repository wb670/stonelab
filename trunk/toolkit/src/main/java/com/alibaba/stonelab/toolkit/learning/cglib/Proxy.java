/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.learning.cglib;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2010-10-29
 */
public class Proxy implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println(obj.getClass().getName() + ":" + StringUtils.join(args));
        Object ret = proxy.invokeSuper(obj, args);
        return ret;
    }

}
