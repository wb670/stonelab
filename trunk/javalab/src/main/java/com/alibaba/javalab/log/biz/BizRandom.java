/**
 * Function: 
 * 
 * File Created at 2010-2-2
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.log.biz;

/**
 * TODO Comment of Random
 * 
 * @author li.jinl
 */
public class BizRandom {

    private static final java.util.Random RANDOM = new java.util.Random(10);

    public static boolean isSuccessful() {
        return RANDOM.nextInt() > 5;
    }

}
