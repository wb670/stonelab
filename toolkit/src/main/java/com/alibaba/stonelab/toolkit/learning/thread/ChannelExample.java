/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.learning.thread;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

/**
 * @author Stone.J 2010-9-8 上午09:38:42
 */
public class ChannelExample {

    public static void main(String[] args) throws Exception {
        // read();
        // write();
        // copy();
        // fastcopy();
    }

    public static void read() throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        FileInputStream in = new FileInputStream("d:/tmp/cha.txt");
        FileChannel channel = in.getChannel();
        while (channel.read(buffer) != -1) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.println(buffer.get());
            }
            buffer.clear();
        }
        channel.close();
        in.close();
    }

    public static void write() throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        FileOutputStream out = new FileOutputStream("d:/tmp/cha_w.txt");
        FileChannel channel = out.getChannel();
        for (int i = 0; i < 10; i++) {
            buffer.put((byte) ('a' + i));
        }
        buffer.flip();
        channel.write(buffer);
        channel.close();
        out.close();
    }

    public static void copy() throws Exception {
        String src = "d:/tmp/mouse.gif";
        String dst = "d:/tmp/mouse.bak.gif";

        FileInputStream fin = new FileInputStream(src);
        FileOutputStream fout = new FileOutputStream(dst);
        FileChannel fcin = fin.getChannel();
        FileChannel fcout = fout.getChannel();

        // ByteBuffer buffer = ByteBuffer.allocate(436073);
        ByteBuffer buffer = ByteBuffer.allocateDirect(436073);
        while (fcin.read(buffer) != -1) {
            buffer.flip();
            fcout.write(buffer);
            buffer.clear();
        }

        fcin.close();
        fcout.close();
        fin.close();
        fout.close();
    }

    public static void fastcopy() throws Exception {
        String src = "d:/tmp/mouse.gif";
        String dst = "d:/tmp/mouse.bak.gif";

        FileInputStream fin = new FileInputStream(src);
        FileOutputStream fout = new FileOutputStream(dst);
        FileChannel fcin = fin.getChannel();
        FileChannel fcout = fout.getChannel();

        MappedByteBuffer buffer = fcin.map(MapMode.READ_ONLY, 0, 436073);

        fcout.write(buffer);

        fcin.close();
        fcout.close();
        fin.close();
        fout.close();
    }

}
