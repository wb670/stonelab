/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.learning.examples;

import java.util.Random;

import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

/**
 * @author li.jinl 2010-7-14 07:56:00
 */
public class JmeterSample extends AbstractJavaSamplerClient {

    private static final Random RANDOM = new Random();

    @Override
    public SampleResult runTest(JavaSamplerContext ctx) {
        int t = ctx.getIntParameter("time");
        SampleResult result = new SampleResult();
        result.sampleStart();
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
        }
        if (RANDOM.nextInt(100) <= 90) {
            result.setSuccessful(true);
        } else {
            result.setSuccessful(false);
        }
        result.sampleEnd();
        return result;
    }

}
