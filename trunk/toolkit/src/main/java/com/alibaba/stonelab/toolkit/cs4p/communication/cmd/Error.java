/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.cs4p.communication.cmd;

/**
 * @author Stone.J 2010-9-17 下午09:54:08
 */
public class Error extends BaseCmd implements Cmd {

    @Override
    public Cmd parser(String cmd) {
        return this;
    }

    @Override
    public String response() {
        return "usage:[binfo][pinfo][padd name x y][pmove x y][pfire x y]";
    }

}
