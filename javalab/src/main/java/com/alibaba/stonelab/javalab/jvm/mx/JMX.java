/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.jvm.mx;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.sun.management.HotSpotDiagnosticMXBean;

/**
 * @author stone 2011-6-24 上午11:21:20
 */
public class JMX {

    private static final String URL         = "service:jmx:rmi:///jndi/rmi://10.20.156.49:9999/jmxrmi";
    private static final String OBJECT_NAME = "com.sun.management:type=HotSpotDiagnostic";

    public static void main(String[] args) throws Exception {
        JMXConnector connector = JMXConnectorFactory.connect(new JMXServiceURL(URL));
        MBeanServerConnection con = connector.getMBeanServerConnection();
        HotSpotDiagnosticMXBean mx = (HotSpotDiagnosticMXBean) ManagementFactory.newPlatformMXBeanProxy(con,
                                                                                                        OBJECT_NAME,
                                                                                                        HotSpotDiagnosticMXBean.class);

        mx.setVMOption("HeapDumpAfterFullGC", "true");
        mx.setVMOption("HeapDumpBeforeFullGC", "true");
        connector.close();
    }

}
