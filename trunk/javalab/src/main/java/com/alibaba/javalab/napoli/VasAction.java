/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.javalab.napoli;

/**
 * @author Maurice.J Dec 2, 2009 4:07:31 PM
 */
public class VasAction implements Runnable {

    private String message;

    public void run() {
        System.out.println(message);
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
