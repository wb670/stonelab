/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.cs4p.communication.cmd;

import net.sf.json.JSONObject;

import com.alibaba.stonelab.toolkit.cs4p.model.member.SimplePlayer;

/**
 * @author Stone.J 2010-9-17 下午09:50:22
 */
public class PlayerInfo extends BaseCmd implements Cmd {

    private static final String CMD = "pinfo";

    @Override
    public Cmd parser(String cmd) {
        if (CMD.equals(cmd)) {
            return this;
        }
        return null;
    }

    @Override
    public String response() {
        SimplePlayer m = (SimplePlayer) session.getPlayer();
        return JSONObject.fromObject(m).toString();
    }

}
