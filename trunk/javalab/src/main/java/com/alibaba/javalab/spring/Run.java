/**
 * Function: 
 * 
 * File Created at 2010-1-14
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * TODO Comment of Run
 * 
 * @author li.jinl
 */
public class Run {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext();
        ctx.addBeanFactoryPostProcessor(new MyBeanFactoryPostProcessor());
        ctx.setConfigLocation("spring/bean.xml");
        ctx.refresh();
        ctx.getBean("javaBean");
    }

    private static class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
                throws BeansException {
            System.out.println("hello");
        }

    }

}
