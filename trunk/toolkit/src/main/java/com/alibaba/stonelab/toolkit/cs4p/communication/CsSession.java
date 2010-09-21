/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.cs4p.communication;

import com.alibaba.stonelab.toolkit.cs4p.model.Member;
import com.alibaba.stonelab.toolkit.cs4p.model.Statistics;
import com.alibaba.stonelab.toolkit.cs4p.model.map.SimpleBattlefield;

/**
 * @author Stone.J 2010-9-17 下午06:22:45
 */
public class CsSession {

    private SimpleBattlefield battlefield;
    private Member            player;
    private Statistics        statistics = Statistics.getInstance();

    public SimpleBattlefield getBattlefield() {
        return battlefield;
    }

    public void setBattlefield(SimpleBattlefield battlefield) {
        this.battlefield = battlefield;
    }

    public Member getPlayer() {
        return player;
    }

    public void setPlayer(Member player) {
        this.player = player;
    }

    public Statistics getStatistics() {
        return statistics;
    }

}
