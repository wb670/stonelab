/*
 * Copyright 1999-2011 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.common.util;

/**
 * <pre>
 * Hash algorithm by Bob Jenkins, 1996.
 * 
 * You may use this code any way you wish, private, educational, or commerical. It's free.
 * See: http://burtleburtle.net/bob/hash/doobs.html
 * 
 * Use for hash table lookup, or anything where one collision in 2^^32 is acceptable.
 * Do not use for cryptographic purposes.
 * 
 * Copyed from Gray Watson http://256.com/gray/
 * 
 * Change Point:
 * 1. Use friendly code format
 * 2. Add comments
 * 
 * Note：
 * 1. thread-unsafe
 * </pre>
 * 
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-1-26
 */
public class JenkinsHash {

    // max value to limit it to 4 bytes
    private static final long MAX_VALUE = 0xFFFFFFFFL;

    // internal variables used in various calculations
    private long              a, b, c;

    /**
     * <pre>
     * Hash a veriable-length key into a 32-bit value.
     * Every bit of the key affects every bit of the return value.
     * Every 1-bit and 2-bit delta achieves avalanche.
     * The best hash table sizes are powers of 2.
     * </pre>
     * 
     * @param buffer Byte array that we are hashing on.
     * @param initialValue Initial value of the hash if we are continuing from a previous run. 0 if noe.
     * @return Hash value for the buffer
     */
    public long hash(byte[] buffer, long initialValue) {
        int len, pos;
        // set up the internal state
        // the golden ratio; an arbitrary value
        a = 0x9e3779b9L;
        // the golden ratio; an arbitrary value
        b = 0x9e3779b9L;
        // the previous hash value
        c = initialValue;
        // handle most of the key
        pos = 0;
        for (len = buffer.length; len >= 12; len -= 12) {
            a = add(a, fourByteToLong(buffer, pos));
            b = add(b, fourByteToLong(buffer, pos + 4));
            c = add(c, fourByteToLong(buffer, pos + 8));
            hashMix();
            pos += 12;
        }
        c += buffer.length;

        // all the case statements fall through to the next on purpose
        switch (len) {
            case 11:
                c = add(c, leftShift(byteToLong(buffer[pos + 10]), 24));
            case 10:
                c = add(c, leftShift(byteToLong(buffer[pos + 9]), 16));
            case 9:
                c = add(c, leftShift(byteToLong(buffer[pos + 8]), 8));
                // the first byte of c is reserved for the length
            case 8:
                b = add(b, leftShift(byteToLong(buffer[pos + 7]), 24));
            case 7:
                b = add(b, leftShift(byteToLong(buffer[pos + 6]), 16));
            case 6:
                b = add(b, leftShift(byteToLong(buffer[pos + 5]), 8));
            case 5:
                b = add(b, byteToLong(buffer[pos + 4]));
            case 4:
                a = add(a, leftShift(byteToLong(buffer[pos + 3]), 24));
            case 3:
                a = add(a, leftShift(byteToLong(buffer[pos + 2]), 16));
            case 2:
                a = add(a, leftShift(byteToLong(buffer[pos + 1]), 8));
            case 1:
                a = add(a, byteToLong(buffer[pos + 0]));
                // case 0: nothing left to add
        }
        hashMix();
        return c;
    }

    /**
     * @see JenkinsHash#hash(byte[], long)
     * @param buffer Byte array that we are hashing on.
     * @return Hash value for the buffer.
     */
    public long hash(byte[] buffer) {
        return hash(buffer, 0);
    }

    // convert a byte into a long value without making it negative.
    private long byteToLong(byte b) {
        // 0x7F => 0111 1111
        // 0x80 => 1000 0000
        long val = b & 0x7F;
        if ((b & 0x80) != 0) {
            val += 128;
        }
        return val;
    }

    // do addition adn turn into 4 bytes
    private long add(long val, long add) {
        return (val + add) & MAX_VALUE;
    }

    // do subtraction adn turn into 4 bytes
    private long subtract(long val, long subtract) {
        return (val - subtract) & MAX_VALUE;
    }

    // left shift val by shift bits and turn into 4 bytes
    private long xor(long val, long xor) {
        return (val ^ xor) & MAX_VALUE;
    }

    // left shift val by shift bits and cut down to 4 bytes
    private long leftShift(long val, int shift) {
        return (val << shift) & MAX_VALUE;
    }

    // convert 4 bytes from the buffer at offset into a long value
    private long fourByteToLong(byte[] bytes, int offset) {
        long val = 0;
        val += byteToLong(bytes[offset + 0]);
        val += byteToLong(bytes[offset + 1]) << 8;
        val += byteToLong(bytes[offset + 2]) << 16;
        val += byteToLong(bytes[offset + 3]) << 24;
        return val;
    }

    // mix up the values int the hash function.
    private void hashMix() {
        a = subtract(a, b);
        a = subtract(a, c);
        a = xor(a, c >> 13);
        b = subtract(b, c);
        b = subtract(b, a);
        b = xor(b, leftShift(a, 8));
        c = subtract(c, a);
        c = subtract(c, b);
        c = xor(c, (b >> 13));
        a = subtract(a, b);
        a = subtract(a, c);
        a = xor(a, (c >> 12));
        b = subtract(b, c);
        b = subtract(b, a);
        b = xor(b, leftShift(a, 16));
        c = subtract(c, a);
        c = subtract(c, b);
        c = xor(c, (b >> 5));
        a = subtract(a, b);
        a = subtract(a, c);
        a = xor(a, (c >> 3));
        b = subtract(b, c);
        b = subtract(b, a);
        b = xor(b, leftShift(a, 10));
        c = subtract(c, a);
        c = subtract(c, b);
        c = xor(c, (b >> 15));
    }
}
