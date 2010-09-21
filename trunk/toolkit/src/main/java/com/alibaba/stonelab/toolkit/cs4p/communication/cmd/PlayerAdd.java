/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.cs4p.communication.cmd;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.alibaba.stonelab.toolkit.cs4p.model.CsException;
import com.alibaba.stonelab.toolkit.cs4p.model.Point;
import com.alibaba.stonelab.toolkit.cs4p.model.member.SimplePlayer;

/**
 * @author Stone.J 2010-9-17 下午10:06:37
 */
public class PlayerAdd extends BaseCmd implements Cmd {

    private static final String CMD = "padd";

    private String              name;
    private long                x;
    private long                y;

    @Override
    public Cmd parser(String cmd) {
        String[] token = cmd.split(" ");
        if (token.length != 4) {
            return null;
        }
        if (!CMD.equals(token[0])) {
            return null;
        }
        if (!(StringUtils.isNumeric(token[2]) && StringUtils.isNumeric(token[3]))) {
            return null;
        }
        this.setName(token[1]);
        this.setX(Long.valueOf(token[2]));
        this.setY(Long.valueOf(token[3]));
        return this;
    }

    @Override
    public String response() {
        if (session.getPlayer() != null) {
            return CsException.Code.MULTI_PLAYERS.getDes();
        }
        SimplePlayer player = new SimplePlayer(name, name, session.getBattlefield());
        player.add(new Point(x, y));
        session.setPlayer(player);
        return JSONObject.fromObject(player).toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
