package com.alibaba.helloworld.example.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Application Lifecycle Listener implementation class VersionListener
 */
public class VersionListener implements ServletContextListener {

    private static final String ATTR_VERSION = "__VERSION__";
    private static final String VERSION      = "1.0";

    /**
     * Default constructor.
     */
    public VersionListener() {
    }

    /**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("Version Inited.");
        event.getServletContext().setAttribute(ATTR_VERSION, VERSION);
    }

    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("Version Destroyed.");
        event.getServletContext().removeAttribute(ATTR_VERSION);
    }

}
