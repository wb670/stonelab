/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.stonelab.webxsample.sample.helloworld;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.alibaba.citrus.springext.support.context.XmlApplicationContext;

/**
 * @author Stone.J 2010-8-25 ÉÏÎç11:07:39
 */
public class CustomedSchemaMain {

    private static final String   LOCATION = "helloworld/custom-schema.xml";
    private static final Resource RES      = new ClassPathResource(LOCATION);

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new XmlApplicationContext(RES);
        CustomService customService = (CustomService) ctx.getBean("customService");
        System.out.println(customService.hello());
    }

}
