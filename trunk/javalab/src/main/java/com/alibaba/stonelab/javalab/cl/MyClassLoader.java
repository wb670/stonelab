/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.cl;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2013-7-9
 */
public class MyClassLoader extends ClassLoader {

    private String url;

    public MyClassLoader(String url){
        super(null);
        this.url = url;

    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String clazz = url + name.replace('.', '/').concat(".class");
        try {
            InputStream in = new FileInputStream(clazz);
            byte[] buffer = new byte[1024 * 1024];
            int len = in.read(buffer);
            in.close();
            return defineClass(null, buffer, 0, len);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
