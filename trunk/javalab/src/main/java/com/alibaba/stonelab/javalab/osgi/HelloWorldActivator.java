/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> Jan 30, 2012
 */
public class HelloWorldActivator implements BundleActivator {

    private HelloWorldService helloWorldService = new HelloWorldServiceImpl();

    @Override
    public void start(BundleContext context) throws Exception {
        context.registerService(HelloWorldService.class.getName(), helloWorldService, null);
        System.out.println("Done.");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
    }

}
