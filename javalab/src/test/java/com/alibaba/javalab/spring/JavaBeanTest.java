/**
 * Function: 
 * 
 * File Created at 2010-5-13
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * @author li.jinl
 */
@ContextConfiguration(locations = "classpath:bean/bean.xml")
public class JavaBeanTest extends AbstractJUnit4SpringContextTests {

    private static final Log log = LogFactory.getLog(JavaBeanTest.class);

    @Autowired
    private JavaBean         javaBean;

    @Test
    public void testInit() {
        log.info(javaBean);
    }
}
