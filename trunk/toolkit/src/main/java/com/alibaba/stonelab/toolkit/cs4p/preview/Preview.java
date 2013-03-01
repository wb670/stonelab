/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.cs4p.preview;

import java.util.Collection;

import com.alibaba.stonelab.toolkit.cs4p.model.Battlefield;
import com.alibaba.stonelab.toolkit.cs4p.model.CsException;
import com.alibaba.stonelab.toolkit.cs4p.model.Member;
import com.alibaba.stonelab.toolkit.cs4p.model.Point;
import com.alibaba.stonelab.toolkit.cs4p.model.map.SimpleBattlefield;
import com.alibaba.stonelab.toolkit.cs4p.model.member.SimplePlayer;

/**
 * @author Stone.J 2010-9-17 下午03:00:36
 */
public class Preview {

	private static SimpleBattlefield battlefield = SimpleBattlefield.getInstance();

	public static void main(String[] args) {
		// 初始化
		Member m1 = new SimplePlayer("STONE", "STONE", battlefield);
		Member m2 = new SimplePlayer("STONE", "FK", battlefield);
		snapshot("init", battlefield, m1, m2);
		// 上战场
		m1.add(new Point(1, 1));
		m2.add(new Point(100, 100));
		snapshot("add", battlefield, m1, m2);
		// 开火
		m1.fire(new Point(99, 99));
		snapshot("fire", battlefield, m1, m2);
		// 死人开火
		try {
			m2.fire(new Point(1, 1));
		} catch (CsException e) {
			System.out.println(e.getCode().getDes());
		}
	}

	public static void snapshot(String info, Battlefield battlefield, Member... members) {
		System.out.println("================" + info + "=====================");
		for (Member member : members) {
			memberSnapshot(member);
		}
		battleSnapshot(battlefield);
		System.out.println("=====================================");
	}

	public static void memberSnapshot(Member member) {
		System.out.println(member);
	}

	public static void battleSnapshot(Battlefield battlefield) {
		Collection<Member> members = battlefield.getMembers();
		for (Member member : members) {
			System.out.print("Battlefield: ");
			memberSnapshot(member);
		}
	}
}
