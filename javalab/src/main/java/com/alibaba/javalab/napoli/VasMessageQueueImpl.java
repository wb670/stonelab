/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.javalab.napoli;

import com.alibaba.china.biz.messagequeue.napoli.AbstractNapoliMessageQueueImpl;

/**
 * @author Maurice.J Dec 1, 2009 2:02:36 PM
 */
public class VasMessageQueueImpl extends AbstractNapoliMessageQueueImpl implements VasMessageQueue {

    private static final String VAS_INVOICE   = "VasInvoice";
    private static final String VAS_AGREEMENT = "VasAgreement";

    public void sendInvoice(String message) {
        transfer.sendMessage(VAS_INVOICE, message);
    }

    public void sendAgreement(String message) {
        transfer.sendMessage(VAS_AGREEMENT, message);
    }

}
