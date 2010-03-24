/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.javalab.napoli;

/**
 * @author Maurice.J Dec 2, 2009 1:30:20 PM
 */
public interface VasMessageQueue {

    public abstract void sendInvoice(String message);

    public abstract void sendAgreement(String message);

}
