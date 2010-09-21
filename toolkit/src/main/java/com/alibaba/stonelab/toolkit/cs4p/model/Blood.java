/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.cs4p.model;

/**
 * @author Stone.J 2010-9-19 下午04:05:04
 */
public class Blood {

    private static final int DEFAULT_TOTAL = 100;
    private static final int DEFAULT_EMPTY = 0;

    private int              total;
    private int              value;

    public Blood(int total){
        this.total = total;
    }

    public Blood(){
        this(DEFAULT_TOTAL);
    }

    public void add(int v) {
        if (value + v > total) {
            v = total;
        }
        value += v;
    }

    public void reduce(int v) {
        if (value - v < DEFAULT_EMPTY) {
            value = DEFAULT_EMPTY;
        }
        value -= v;
    }

    public boolean isEmpty() {
        return value == DEFAULT_EMPTY;
    }

}
