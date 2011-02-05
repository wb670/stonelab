/*
 * Copyright 1999-2011 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.aliinc;

import javax.net.ssl.SSLSocketFactory;

import junit.framework.TestCase;

import com.alibaba.stonelab.javalab.common.http.SimpleSSLSocketFacotryContext;
import com.alibaba.stonelab.javalab.common.http.SocketFactoryContext;

/**
 * simple ssl socket factory context test
 * 
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-1-19
 */
public class SimpleSSLSocketFacotryContextTest extends TestCase {

    private String ksfile  = "aliinc/jl.store";
    private String kspwd   = "123456";
    private String tksfile = "aliinc/alibaba.store";
    private String tkspwd  = "123456";

    public void test_createSocketFactory_ok() {
        SocketFactoryContext ctx = new SimpleSSLSocketFacotryContext(ksfile, kspwd, tksfile, tkspwd);
        assertNotNull(ctx.createSocketFactory());
        assertTrue(SSLSocketFactory.class.isAssignableFrom(ctx.createSocketFactory().getClass()));
    }
}
