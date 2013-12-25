/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.misc.dubbo;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.taobao.common.stable.CtSph;
import com.taobao.common.stable.Sph;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2013-7-9
 */
public class Protection implements MethodInterceptor {

    private Sph sph = new CtSph("CtSph", 2);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println(invocation.getMethod().getDeclaringClass());
        System.out.println(invocation.getThis().getClass());
        if (sph.entry()) {
            try {
                System.out.println("sph#entry");
                return invocation.proceed();
            } finally {
                System.out.println("sph#release");
                sph.release();
            }

        }
        throw new RuntimeException("Blocked.");
    }

}
