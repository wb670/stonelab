/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.cs4p.communication.cmd;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.alibaba.stonelab.toolkit.cs4p.model.Point;

/**
 * @author Stone.J 2010-9-17 下午07:03:05
 */
public class PlayerMove extends BaseCmd implements Cmd {

    private static final String CMD = "pmove";

    public long                 x;
    public long                 y;

    @Override
    public Cmd parser(String cmd) {
        String[] token = cmd.split(" ");
        if (token.length != 3) {
            return null;
        }
        if (!CMD.equals(token[0])) {
            return null;
        }
        if (!(StringUtils.isNumeric(token[1]) && StringUtils.isNumeric(token[2]))) {
            return null;
        }
        this.setX(Long.valueOf(token[1]));
        this.setY(Long.valueOf(token[2]));
        return this;
    }

    @Override
    public String response() {
        session.getPlayer().move(new Point(x, y));
        return JSONObject.fromObject(session.getPlayer()).toString();
    }

    public long getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

}
