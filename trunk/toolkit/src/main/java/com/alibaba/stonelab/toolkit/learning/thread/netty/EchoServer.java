/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.learning.thread.netty;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipelineCoverage;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**
 * @author Stone.J 2010-9-16 上午11:03:09
 */
public class EchoServer {

    private static final ChannelBuffer BUFFER = ChannelBuffers.buffer(1024);
    private static final Charset       GBK    = Charset.forName("GBK");

    public static void main(String[] args) {
        NioServerSocketChannelFactory factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
                                                                                  Executors.newCachedThreadPool());
        ServerBootstrap bootstrap = new ServerBootstrap(factory);
        bootstrap.getPipeline().addLast("echoHandler", new EchoHandler());
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);
        bootstrap.bind(new InetSocketAddress(9999));
    }

    @ChannelPipelineCoverage("all")
    public static class EchoHandler extends SimpleChannelHandler {

        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
            ChannelBuffer buffer = (ChannelBuffer) e.getMessage();
            byte[] msg = new byte[buffer.readableBytes()];
            buffer.readBytes(msg);
            System.out.print("Client:" + new String(msg, GBK));

            BUFFER.clear();
            BUFFER.writeBytes(msg);
            e.getChannel().write(BUFFER);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
            e.getCause().printStackTrace();
        }

    }
}
