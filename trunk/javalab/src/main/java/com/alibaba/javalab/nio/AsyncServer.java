/**
 * Function: 
 * 
 * File Created at 2010-3-28
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Set;

/**
 * @author li.jinl
 */
public class AsyncServer {

    private static final int    PORT = 9999;

    private Selector            selector;
    private ServerSocketChannel ssc;

    public static void main(String[] args) throws Exception {
        AsyncServer server = new AsyncServer();
        server.init();
        server.start();
    }

    public void init() throws Exception {
        selector = Selector.open();
        ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.socket().bind(new InetSocketAddress(PORT));
        ssc.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void start() throws Exception {
        while (selector.select() > 0) {
            Thread.sleep(500);
            Set<SelectionKey> readys = selector.selectedKeys();
            for (SelectionKey key : readys) {
                //ssc 接受到连接
                if (key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);
                }
                //client 
                if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(8);
                    ByteBuffer writeBuffer = ByteBuffer.allocate(64);
                    int len = 0;
                    len = sc.read(buffer);
                    if (len > 0) {
                        buffer.flip();
                        String op = new String(buffer.array(), 0, buffer.limit());
                        //time
                        if ("time".equals(op)) {
                            writeBuffer.put(new Date().toString().getBytes());
                        }
                        //error
                        else {
                            writeBuffer.put("ERROR".getBytes());
                        }
                        writeBuffer.flip();
                        sc.write(writeBuffer);
                        writeBuffer.clear();
                    }
                }
            }
            readys.clear();
        }
    }
}
