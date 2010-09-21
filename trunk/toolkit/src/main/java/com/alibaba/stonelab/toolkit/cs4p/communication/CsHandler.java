/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.cs4p.communication;

import java.nio.charset.Charset;
import java.util.List;

import org.jboss.netty.buffer.BigEndianHeapChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipelineCoverage;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.alibaba.stonelab.toolkit.cs4p.communication.cmd.Cmd;
import com.alibaba.stonelab.toolkit.cs4p.communication.cmd.CmdFactory;
import com.alibaba.stonelab.toolkit.cs4p.model.CsException;
import com.alibaba.stonelab.toolkit.cs4p.model.Member;
import com.alibaba.stonelab.toolkit.cs4p.model.map.SimpleBattlefield;

/**
 * @author Stone.J 2010-9-17 下午09:57:24
 */
@ChannelPipelineCoverage("all")
public class CsHandler extends SimpleChannelHandler {

    private static final Charset DEFAULT_CHARSET = Charset.forName("GBK");

    private SimpleBattlefield    battlefield     = SimpleBattlefield.getInstance();
    private CsSessionManager     sessionManager  = CsSessionManager.getInstance();

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        CsSession session = sessionManager.get(e.getChannel());
        session.setBattlefield(battlefield);
        sessionManager.add(e.getChannel(), session);
    }

    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        CsSession session = sessionManager.get(e.getChannel());
        Member m = session.getPlayer();
        if (m != null) {
            m.remove();
        }
        sessionManager.remove(e.getChannel());
        e.getChannel().close();
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        ChannelBuffer buffer = (ChannelBuffer) e.getMessage();
        String cmd = decode(buffer);
        // log
        log(e.getChannel(), cmd);

        List<Cmd> cmds = CmdFactory.getCmds();
        for (Cmd c : cmds) {
            if (c.parser(cmd) != null) {
                c.setSession(sessionManager.get(e.getChannel()));
                String reps = c.response();
                e.getChannel().write(encode(reps));
                break;
            }
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        if (e.getCause() instanceof CsException) {
            CsException exception = (CsException) e.getCause();
            e.getChannel().write(encode(exception.getCode().getDes()));
            return;
        }
        sessionManager.get(e.getChannel()).getPlayer().remove();
        e.getChannel().close();
        e.getCause().printStackTrace();
    }

    private String decode(ChannelBuffer buffer) {
        byte[] msg = new byte[buffer.readableBytes()];
        buffer.readBytes(msg);
        String cmd = new String(msg, DEFAULT_CHARSET);
        return cmd.trim();
    }

    private ChannelBuffer encode(String msg) {
        msg += "\r\n";
        ChannelBuffer buffer = new BigEndianHeapChannelBuffer(msg.getBytes(DEFAULT_CHARSET));
        return buffer;
    }

    private void log(Channel channel, String cmd) {
        CsSession session = sessionManager.get(channel);
        Member player = session.getPlayer();
        String name = (player == null) ? "unkown" : player.getInfo().getName();
        StringBuilder log = new StringBuilder(64);
        log.append(name).append(":");
        log.append(cmd).append(":");
        log.append(channel.toString());
        System.out.println(log.toString());
    }

}
