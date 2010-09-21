/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.learning.thread.netty;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipelineCoverage;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

/**
 * @author Stone.J 2010-9-16 下午02:55:17
 */
public class EchoClient {

    private static final String        HOST   = "10.20.156.49";
    private static final int           PORT   = 9999;

    private static final ChannelBuffer BUFFER = ChannelBuffers.buffer(1024);
    private static final Charset       GBK    = Charset.forName("GBK");

    public static void main(String[] args) {
        NioClientSocketChannelFactory factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),
                                                                                  Executors.newCachedThreadPool());
        ClientBootstrap bootstrap = new ClientBootstrap(factory);
        SimpleChannelHandler handler = new EchoHandler();
        bootstrap.getPipeline().addLast("echoHandler", handler);

        ChannelFuture future = bootstrap.connect(new InetSocketAddress(HOST, PORT));

        BUFFER.clear();
        BUFFER.writeBytes("abcd\n".getBytes(GBK));
        future.awaitUninterruptibly().getChannel().write(BUFFER);

        BUFFER.clear();
        BUFFER.writeBytes("abcd\n".getBytes(GBK));
        future.awaitUninterruptibly().getChannel().write(BUFFER);
        
        pause(1);
        future.awaitUninterruptibly().getChannel().close();
        
        bootstrap.releaseExternalResources();
    }

    public static void pause(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
        }
    }

    @ChannelPipelineCoverage("all")
    public static class EchoHandler extends SimpleChannelHandler {

        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            System.out.println("connect");
        }

        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
            ChannelBuffer buffer = (ChannelBuffer) e.getMessage();
            byte[] msg = new byte[buffer.readableBytes()];
            buffer.readBytes(msg);
            System.out.print(new String(msg, "GBK"));
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
            e.getCause().printStackTrace();
        }
    }
}
