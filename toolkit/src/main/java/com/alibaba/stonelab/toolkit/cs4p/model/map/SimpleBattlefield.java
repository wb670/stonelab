/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.cs4p.model.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.util.Assert;

import com.alibaba.stonelab.toolkit.cs4p.model.Area;
import com.alibaba.stonelab.toolkit.cs4p.model.Battlefield;
import com.alibaba.stonelab.toolkit.cs4p.model.Member;

/**
 * 简单战场
 * 
 * @author Stone.J 2010-9-17 下午01:21:19
 */
public class SimpleBattlefield implements Battlefield {

    private static final SimpleBattlefield BATTLEFIELD = new SimpleBattlefield(1024, 1024);

    // 战场地图长
    private long                           length;
    // 战场地图宽
    private long                           width;

    // 队员信息
    private Collection<Member>             members     = new ConcurrentLinkedQueue<Member>();

    public static final SimpleBattlefield getInstance() {
        return BATTLEFIELD;
    }

    private SimpleBattlefield(long length, long width){
        this.length = length;
        this.width = width;
    }

    @Override
    public void addMember(Member m) {
        Assert.notNull(m, "member is null.");
        members.add(m);
    }

    @Override
    public void removeMember(Member m) {
        Assert.notNull(m, "member is null.");
        members.remove(m);
    }

    @Override
    public Collection<Member> getMembers() {
        return members;
    }

    @Override
    public Collection<Member> getFiredMembers(Area area) {
        Collection<Member> firedMembers = new ArrayList<Member>();
        for (Member member : members) {
            if (member.getLocation().isCross(area)) {
                firedMembers.add(member);
            }
        }
        return firedMembers;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getWidth() {
        return width;
    }

    public void setWidth(long width) {
        this.width = width;
    }

}
