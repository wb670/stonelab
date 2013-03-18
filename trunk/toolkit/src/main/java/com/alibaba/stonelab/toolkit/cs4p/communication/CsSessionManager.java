/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.cs4p.communication;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.netty.channel.Channel;

/**
 * @author Stone.J 2010-9-17 下午09:58:00
 */
public class CsSessionManager {

	private static final CsSessionManager MANAGER = new CsSessionManager();

	private Map<Channel, CsSession> store = new ConcurrentHashMap<Channel, CsSession>();

	public static final CsSessionManager getInstance() {
		return MANAGER;
	}

	private CsSessionManager() {
	}

	public void add(Channel channel, CsSession session) {
		store.put(channel, session);
	}

	public void remove(Channel channel) {
		CsSession session = store.get(channel);
		if (session != null) {
			if (session.getPlayer() != null) {
				session.getPlayer().remove();
			}
		}
		store.remove(channel);
	}

	public CsSession get(Channel channel) {
		CsSession session = store.get(channel);
		if (session == null) {
			session = new CsSession();
		}
		return session;
	}

}
