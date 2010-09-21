/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.learning.thread.netty;

import java.nio.channels.Channel;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stone.J 2010-9-17 上午10:52:57
 */
public class SessionManager {

    private Map<Integer, Session> sessions = new HashMap<Integer, Session>();

    public Session get(int id) {
        return sessions.get(id);
    }

    public void remove(int id) {
        sessions.remove(id);
    }

    public static class Session {

        private int     id;
        private Channel channel;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Channel getChannel() {
            return channel;
        }

        public void setChannel(Channel channel) {
            this.channel = channel;
        }

    }
}
