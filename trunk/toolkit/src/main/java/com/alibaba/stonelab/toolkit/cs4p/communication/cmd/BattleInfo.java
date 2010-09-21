/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.cs4p.communication.cmd;

import net.sf.json.JSONObject;

import com.alibaba.stonelab.toolkit.cs4p.model.map.SimpleBattlefield;

/**
 * @author Stone.J 2010-9-17 下午06:27:05
 */
public class BattleInfo extends BaseCmd implements Cmd {

    private static final String CMD = "binfo";

    @Override
    public Cmd parser(String cmd) {
        if (CMD.equals(cmd)) {
            return this;
        }
        return null;
    }

    @Override
    public String response() {
        SimpleBattlefield battlefield = session.getBattlefield();
        return JSONObject.fromObject(battlefield).toString();
    }
}
