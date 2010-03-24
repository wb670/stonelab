/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.javalab.napoli.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;

import com.alibaba.javalab.napoli.VasMessageQueue;

/**
 * @author Maurice.J Dec 1, 2009 2:18:00 PM
 */
@ContextConfiguration(locations = "classpath:com/alibaba/javalab/napoli/biz-napoli.xml")
public class SenderTest extends AbstractJUnit38SpringContextTests {

    private static final int COUNT = 100;

    @Autowired
    private VasMessageQueue  vasMessageQueue;

    @Test
    public void testSendInvoice() {
        for (int i = 0; i < COUNT; i++) {
            vasMessageQueue.sendInvoice("invoice" + i);
        }
    }

    @Test
    public void testSendAgreement() {
        for (int i = 0; i < COUNT; i++) {
            vasMessageQueue.sendAgreement("agreement" + i);
        }
    }

    public void setVasMessageQueue(VasMessageQueue vasMessageQueue) {
        // this.vasMessageQueue = vasMessageQueue;
    }

}
