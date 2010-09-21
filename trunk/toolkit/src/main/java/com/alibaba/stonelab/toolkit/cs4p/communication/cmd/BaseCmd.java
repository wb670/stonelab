/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.cs4p.communication.cmd;

import com.alibaba.stonelab.toolkit.cs4p.communication.CsSession;

/**
 * @author Stone.J 2010-9-17 下午07:12:07
 */
public abstract class BaseCmd implements Cmd {

    protected CsSession session;

    @Override
    public void setSession(CsSession session) {
        this.session = session;
    }

}
