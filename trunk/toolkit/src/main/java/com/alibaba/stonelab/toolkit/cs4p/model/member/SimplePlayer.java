/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.cs4p.model.member;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

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

	private static final long DEFAULT_RADIUS = 5;
	private static final int BASE_MOVE = 400;
	private static final int BASE_FIRE = 800;

	private Statistics statistics = Statistics.getInstance();

	private SimpleBattlefield battlefield;

	private Info info;
	private Point point;
	private Area body;

	private Weapon weapon = new SimpleWeapon();
	private Random random = new Random();

	public SimplePlayer(String id, String name, SimpleBattlefield battlefield) {
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
		long x = Math.abs(p.getX() - point.getX());
		long y = Math.abs(p.getY() - point.getY());
		if (x > 5 || y > 5) {
			throw new CsException(Code.STEP_OVERFLOW);
		}
		pause(BASE_MOVE + (int) ((x + y) * 10));
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
		pause(BASE_FIRE);
		Map<Member, Integer> members = battlefield.getFiredMembers(weapon.onfire(getFirePoint(p)));
		Set<Entry<Member, Integer>> set = members.entrySet();
		for (Entry<Member, Integer> kv : set) {
			Member m = kv.getKey();
			Integer v = kv.getValue();
			m.getInfo().reduceBlood(v);

			if (m.getInfo().getBlood() <= 0) {
				m.remove();
				statistics.addDie(m.getInfo().getName(), 1);
				statistics.addAnnihilate(getInfo().getName(), 1);
			}
		}
		return members.keySet();
	}

	@Override
	public double getCapable() {
		return getInfo().getBlood() / 100.0;
	}

	// 计算实际打中位置
	private Point getFirePoint(Point p) {
		double distance = Math.pow(
				Math.pow(getPoint().getX() - p.getX(), 2) + Math.pow(getPoint().getY() - p.getY(), 2), 0.5);
		long mistake;
		if (distance > 1000) {
			mistake = 2 + random.nextInt(7);
		} else if (distance > 800) {
			mistake = 1 + random.nextInt(6);
		} else if (distance > 500) {
			mistake = random.nextInt(5);
		} else if (distance > 200) {
			mistake = random.nextInt(4);
		} else {
			mistake = random.nextInt(3);
		}
		long x = p.getX() + mistake * (random.nextInt() > 0.5 ? 1 : -1);
		long y = p.getY() + mistake * (random.nextInt() > 0.5 ? 1 : -1);
		return new Point(x, y);
	}

	// 检查状态
	private void checkStatus() {
		if (!info.isActive()) {
			throw new CsException(Code.INACTIVE);
		}
		if (!info.isLive()) {
			throw new CsException(Code.DEAD);
		}
		if (!battlefield.isBattle()) {
			throw new CsException(Code.NOT_START);
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
	private void pause(int base) {
		try {
			Thread.sleep((long) (base * (2 - getCapable())));
		} catch (InterruptedException e) {
		}
	}
}
