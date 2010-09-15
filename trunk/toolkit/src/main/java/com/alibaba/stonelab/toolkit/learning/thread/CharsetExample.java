/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.learning.thread;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

/**
 * @author Stone.J 2010-9-8 下午03:04:37
 */
public class CharsetExample {

    public static void main(String[] args) throws Exception {
        // read();
        decodeRead();
    }

    public static void read() throws Exception {
        String src = "d:/tmp/gbk.txt";
        FileInputStream fin = new FileInputStream(src);
        FileChannel fc = fin.getChannel();
        MappedByteBuffer buffer = fc.map(MapMode.READ_ONLY, 0, fc.size());

        CharBuffer cb = Charset.forName("GBK").decode(buffer);
        while (cb.hasRemaining()) {
            System.out.print(cb.get());
        }

        fc.close();
        fin.close();
    }

    public static void decodeRead() throws Exception {
        String src = "d:/tmp/gbk.txt";
        ByteBuffer bb = ByteBuffer.allocate(8);
        CharBuffer cb = CharBuffer.allocate(8);
        CharsetDecoder decoder = Charset.forName("GBK").newDecoder();
        File srcfile = new File(src);
        FileInputStream fin = new FileInputStream(srcfile);
        FileChannel fc = fin.getChannel();

        int len;
        while (true) {
            len = fc.read(bb);
            bb.flip();
            CoderResult result = decoder.decode(bb, cb, len < 0);
            if (result.isUnderflow()) {
                bb.compact();
            }

            cb.flip();
            System.out.print(cb.toString());
            cb.clear();

            if (len < 0) {
                break;
            }
        }

        fc.close();
        fin.close();
    }

}
