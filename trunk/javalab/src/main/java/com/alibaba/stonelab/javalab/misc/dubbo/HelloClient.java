/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.misc.dubbo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2013-7-2
 */
public class HelloClient {

    private static ApplicationContext ctx = new ClassPathXmlApplicationContext("misc/dubbo-client.xml");

    public static void main(String[] args) {
        HelloService hello = (HelloService) ctx.getBean("hello");

        System.out.println(hello.say("tinyzen"));
    }

}
