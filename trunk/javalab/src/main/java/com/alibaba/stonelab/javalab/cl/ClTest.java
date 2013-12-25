/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.cl;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2013-7-9
 */
public class ClTest {

    private static final String URL  = "/Users/stone/Shotcuts/work/stonelab/javalab/target/classes/";
    private static final String NAME = "com.alibaba.stonelab.javalab.cl.ClBean1";

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        ClassLoader loader = new MyClassLoader(URL);
        Class<ClBean1> clazz = (Class<ClBean1>) loader.loadClass(NAME);
        Object bean1 = clazz.newInstance();
        System.out.println(bean1);
        System.out.println(bean1.getClass().getClassLoader());
        System.out.println(bean1.getClass().getClassLoader());

    }
}
