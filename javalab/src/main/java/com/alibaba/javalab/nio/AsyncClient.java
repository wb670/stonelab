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
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * @author li.jinl
 */
public class AsyncClient implements Runnable {

    private static final String MSG_TIME    = "time";
    private static final String MSG_CLOSE   = "close";

    private String              host        = "127.0.0.1";
    private int                 port        = 9999;

    private ByteBuffer          readBuffer  = ByteBuffer.allocate(1024);
    private ByteBuffer          writeBuffer = ByteBuffer.allocate(1024);

    private Selector            selector;
    private SocketChannel       sc;

    public static void main(String[] args) throws Exception {
        AsyncClient c = new AsyncClient();
        c.init();
        new Thread(c).start();
        c.send(MSG_TIME);
        Thread.sleep(2000);
        c.send(MSG_CLOSE);
        Thread.sleep(5000);
        c.stop();
    }

    public void init() throws Exception {
        selector = Selector.open();
        sc = SocketChannel.open();
        sc.connect(new InetSocketAddress(host, port));
        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_READ);
    }

    @Override
    public void run() {
        try {
            start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void start() throws Exception {
        while (selector.select() > 0) {
            Set<SelectionKey> readys = selector.selectedKeys();
            for (SelectionKey key : readys) {
                if (key.isReadable()) {
                    sc.read(readBuffer);
                    readBuffer.flip();
                    info(new String(readBuffer.array(), 0, readBuffer.limit()));
                    readBuffer.clear();
                }
            }
            readys.clear();
            Thread.sleep(500);
        }
    }

    public void stop() throws Exception {
        sc.close();
    }

    private void send(String msg) throws Exception {
        writeBuffer.clear();
        writeBuffer.put(msg.getBytes());
        writeBuffer.flip();
        sc.write(writeBuffer);
    }

    private void info(Object info) {
        System.out.println(info);
    }

}
