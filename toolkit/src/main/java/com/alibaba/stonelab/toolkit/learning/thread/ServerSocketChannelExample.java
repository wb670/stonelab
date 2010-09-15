/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.learning.thread;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Stone.J 2010-9-8 下午01:31:19
 */
public class ServerSocketChannelExample {

    private static final int DEFAULT_PORT = 9999;

    private int              port         = DEFAULT_PORT;
    private Selector         selector;
    private ByteBuffer       buffer       = ByteBuffer.allocate(16);

    public static void main(String[] args) {
        new ServerSocketChannelExample();
    }

    public ServerSocketChannelExample(){
        // init
        init();
        // listen
        listen();
    }

    // init
    private void init() {
        try {
            // init selector
            selector = Selector.open();
            // init ssc
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ssc.socket().bind(new InetSocketAddress(port));
            ssc.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("init error.", e);
        }
    }

    // listen
    private void listen() {
        while (true) {
            try {
                // rest
                Thread.sleep(100);
                // do select
                int num = selector.select();
                if (num < 0) {
                    continue;
                }
                // process
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();
                while (it.hasNext()) {
                    // pepare
                    SelectionKey key = it.next();
                    // ssc on accept
                    if (key.isAcceptable()) {
                        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                        SocketChannel sc = ssc.accept();
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ);
                    }
                    // sc on read
                    if (key.isReadable()) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        while (true) {
                            buffer.clear();
                            int len = sc.read(buffer);
                            // exited
                            if (len == -1) {
                                sc.socket().close();
                                sc.close();
                                System.out.println("close.");
                            }
                            // finished
                            if (len <= 0) {
                                break;
                            }
                            buffer.flip();
                            sc.write(buffer);
                        }
                    }
                    // remove
                    it.remove();
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("listen error.", e);
            }
        }
    }
}
