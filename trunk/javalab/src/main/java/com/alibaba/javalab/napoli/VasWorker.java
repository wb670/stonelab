/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.javalab.napoli;

import java.io.Serializable;

import com.alibaba.napoli.client.async.AsyncWorker;

/**
 * VAS message worker
 * 
 * @author Maurice.J Dec 1, 2009 5:50:05 PM
 */
public class VasWorker implements AsyncWorker {

    public boolean doWork(Serializable message) {
        if (message instanceof String) {
            String msg = (String) message;
            System.out.println(msg);
            return true;
        }
        return false;
    }

}
