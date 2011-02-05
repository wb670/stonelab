/*
 * Copyright 1999-2011 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.common.http;

import javax.net.SocketFactory;

/**
 * socket factory context
 * 
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-1-21
 */
public interface SocketFactoryContext {

    /**
     * crete socket factory
     * 
     * @return socketFactory
     */
    public SocketFactory createSocketFactory();

}
