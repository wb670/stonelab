/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.learning.spring.placeholder;

import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.stonelab.toolkit.learning.spring.JavaBean;


/**
 * @author li.jinl 2010-6-30 11:31:43
 */
public class Run {

    private static final String CONFIG = "classpath:spring/bean.xml";

    public static void main(String[] args) {
        Properties props = System.getProperties();
        props.setProperty("node", "Pnode");
        props.setProperty("key", "Pkey");

        ApplicationContext ctx = new ClassPathXmlApplicationContext(CONFIG);
        JavaBean bean = (JavaBean) ctx.getBean("javaBean");
        System.out.println(bean.getName());
    }

}
