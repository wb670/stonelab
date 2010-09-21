/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.cs4p.communication.cmd;

import net.sf.json.JSONArray;

/**
 * @author Stone.J 2010-9-19 下午11:28:08
 */
public class PlayerStatistics extends BaseCmd implements Cmd {

    private static final String CMD = "pstat";

    @Override
    public Cmd parser(String cmd) {
        if (CMD.equals(cmd)) {
            return this;
        }
        return null;
    }

    @Override
    public String response() {
        return JSONArray.fromObject(session.getStatistics().getStatistics()).toString();
    }

}
