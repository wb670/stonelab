/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.cs4p.model.member;

import java.util.Collection;
import java.util.Random;

import org.springframework.util.Assert;

import com.alibaba.stonelab.toolkit.cs4p.model.Area;
import com.alibaba.stonelab.toolkit.cs4p.model.CsException;
import com.alibaba.stonelab.toolkit.cs4p.model.CsException.Code;
import com.alibaba.stonelab.toolkit.cs4p.model.Info;
import com.alibaba.stonelab.toolkit.cs4p.model.Member;
import com.alibaba.stonelab.toolkit.cs4p.model.Point;
import com.alibaba.stonelab.toolkit.cs4p.model.Statistics;
import com.alibaba.stonelab.toolkit.cs4p.model.Weapon;
import com.alibaba.stonelab.toolkit.cs4p.model.area.SimpleArea;
import com.alibaba.stonelab.toolkit.cs4p.model.map.SimpleBattlefield;
import com.alibaba.stonelab.toolkit.cs4p.model.weapon.SimpleWeapon;

/**
 * @author Stone.J 2010-9-17 下午01:41:04
 */
public class SimplePlayer implements Member {

    private static final long   DEFAULT_RADIUS = 5;
    private static final Random RANDOM         = new Random();

    private Statistics          statistics     = Statistics.getInstance();

    private SimpleBattlefield   battlefield;

    private Info                info;
    private Point               point;
    private Area                body;

    private Weapon              weapon         = new SimpleWeapon();

    public SimplePlayer(String id, String name, SimpleBattlefield battlefield){
        this.info = new Info(id, name);
        this.battlefield = battlefield;
    }

    @Override
    public Info getInfo() {
        return info;
    }

    @Override
    public void add(Point p) {
        Assert.notNull(p, "point is null.");
        resize(p);
        this.point = new Point(p.getX(), p.getY());
        this.body = new SimpleArea(this.point, DEFAULT_RADIUS);
        this.battlefield.addMember(this);

        info.setActive(true);
    }

    @Override
    public void move(Point p) {
        checkStatus();
        resize(p);
        // 移动步伐过大
        if (Math.abs(p.getX() - point.getX()) > 5) {
            throw new CsException(Code.STEP_OVERFLOW);
        }
        if (Math.abs(p.getY() - point.getY()) > 5) {
            throw new CsException(Code.STEP_OVERFLOW);
        }
        pause();
        point.setX(p.getX());
        point.setY(p.getY());
    }

    @Override
    public void remove() {
        info.setLive(false);
        battlefield.removeMember(this);
    }

    @Override
    public Point getPoint() {
        return point;
    }

    @Override
    public Area getLocation() {
        return body;
    }

    @Override
    public Collection<Member> fire(Point p) {
        checkStatus();
        pause();
        Collection<Member> members = battlefield.getFiredMembers(weapon.onfire(p));
        for (Member member : members) {
            statistics.addDie(member.getInfo().getName(), 1);
            member.remove();
        }
        statistics.addAnnihilate(info.getName(), members.size());
        return members;
    }

    // 检查状态
    private void checkStatus() {
        if (!info.isActive()) {
            throw new CsException(Code.INACTIVE);
        }
        if (!info.isLive()) {
            throw new CsException(Code.DEAD);
        }
    }

    // 调整p大小,不能超过地图大小
    private void resize(Point p) {
        if (p.getX() > battlefield.getLength()) {
            p.setX(battlefield.getLength());
        }
        if (p.getY() > battlefield.getWidth()) {
            p.setY(battlefield.getWidth());
        }
    }

    // 停滞
    private void pause() {
        int ms = RANDOM.nextInt(100);
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }

}
