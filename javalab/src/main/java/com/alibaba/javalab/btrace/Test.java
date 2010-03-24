/**
 * Function: 
 * 
 * File Created at 2010-2-24
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.btrace;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.util.List;

/**
 * @author li.jinl
 */
public class Test {

    public static void main(String[] args) {
        MemoryMXBean mbean = ManagementFactory.getMemoryMXBean();
        System.out.println(mbean);
        System.out.println(mbean.getHeapMemoryUsage());

        List<MemoryPoolMXBean> pbeans = ManagementFactory.getMemoryPoolMXBeans();
        for (MemoryPoolMXBean pb : pbeans) {
            System.out.println(pb.getName());
        }
    }

}
