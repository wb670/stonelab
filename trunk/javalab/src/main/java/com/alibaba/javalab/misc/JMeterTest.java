/**
 * Function: 
 * 
 * File Created at 2010-6-11
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.misc;

import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

/**
 * @author li.jinl
 */
public class JMeterTest extends AbstractJavaSamplerClient {

    @Override
    public SampleResult runTest(JavaSamplerContext ctx) {
        SampleResult result = new SampleResult();
        result.sampleStart();
        System.out.println("Run Here");
        result.sampleEnd();
        result.setSuccessful(true);
        return result;
    }

}
