/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.cs4p.communication.cmd;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stone.J 2010-9-17 下午10:12:02
 */
public class CmdFactory {

    private static final List<Cmd> CMDS = new ArrayList<Cmd>();

    static {
        CMDS.add(new BattleInfo());
        CMDS.add(new PlayerAdd());
        CMDS.add(new PlayerInfo());
        CMDS.add(new PlayerMove());
        CMDS.add(new PlayerFire());
        CMDS.add(new PlayerStatistics());
        CMDS.add(new Error());
    }

    public static List<Cmd> getCmds() {
        return CMDS;
    }

}
