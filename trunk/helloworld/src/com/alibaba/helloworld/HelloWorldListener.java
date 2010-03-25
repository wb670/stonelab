package com.alibaba.helloworld;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Application Lifecycle Listener implementation class HelloWorldListener
 */
public class HelloWorldListener implements ServletContextListener {

    public HelloWorldListener() {
    }

    public void contextInitialized(ServletContextEvent event) {
        System.out.println("开始了!");
    }

    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("结束了!");
    }

}
