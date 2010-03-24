/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.javalab.napoli.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;

import com.alibaba.napoli.client.NapoliClientException;
import com.alibaba.napoli.client.async.AsyncReceiver;

/**
 * @author Maurice.J Dec 1, 2009 6:01:41 PM
 */
@ContextConfiguration(locations = "classpath:com/alibaba/javalab/napoli/biz-napoli.xml")
public class ReceiverTest extends AbstractJUnit38SpringContextTests {

    @Autowired
    @Qualifier("vasInvoiceReceiver")
    private AsyncReceiver vasInvoiceReceiver;
    @Autowired
    @Qualifier("vasAgreementReceiver")
    private AsyncReceiver vasAgreementReceiver;

    @Test
    public void testInvoiceReceiver() throws NapoliClientException {
        vasInvoiceReceiver.start();
    }

    @Test
    public void testAgreementReceiver() throws NapoliClientException {
        vasAgreementReceiver.start();
    }

    public void setVasInvoiceReceiver(AsyncReceiver vasInvoiceReceiver) {
        this.vasInvoiceReceiver = vasInvoiceReceiver;
    }

    public void setVasAgreementReceiver(AsyncReceiver vasAgreementReceiver) {
        this.vasAgreementReceiver = vasAgreementReceiver;
    }

}
