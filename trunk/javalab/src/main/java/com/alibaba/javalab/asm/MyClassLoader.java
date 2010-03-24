/**
 * Function: 
 * 
 * File Created at 2010-2-21
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.asm;

/**
 * @author li.jinl
 */
public class MyClassLoader extends ClassLoader {

    public Class<?> defineClass(String name, byte[] b) {
        return defineClass(name, b, 0, b.length);
    }

}
