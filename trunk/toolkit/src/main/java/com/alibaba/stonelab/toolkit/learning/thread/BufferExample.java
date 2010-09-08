/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.learning.thread;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 * 类BufferExample.java的实现描述：TODO 类实现描述
 * 
 * @author Stone.J 2010-9-7 下午05:42:14
 */
public class BufferExample {

    public static void main(String[] args) {
        useFloatBuffer();
        bufferType();
    }

    public static void useFloatBuffer() {
        FloatBuffer buffer = FloatBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((float) i);
        }
        buffer.flip();

        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }

    public static void bufferType() {
        System.out.println(ByteBuffer.allocate(10).getClass());
        System.out.println(ByteBuffer.allocateDirect(10).getClass());
    }

}
