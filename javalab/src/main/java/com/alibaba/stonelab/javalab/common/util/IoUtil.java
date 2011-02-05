/*
 * Copyright 1999-2011 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * util for io operations
 * 
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-1-20
 */
public class IoUtil {

    private static final int DEFAULT_BUFFER_SIZE = 1024 * 2;

    /**
     * close inputstream quietly
     */
    public static void close(InputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                // quietly
            }
        }
    }

    /**
     * close outputstream quietly
     * 
     * @param out
     */
    public static void close(OutputStream out) {
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                // quietly
            }
        }
    }

    /**
     * get the data of an InputStream as a String
     * 
     * @param in InputStream
     * @param encoding encoding
     * @return the data of an InputStream
     * @throws IOException
     */
    public static String toString(InputStream in, String encoding) throws IOException {
        InputStreamReader reader = null;
        if (encoding == null) {
            reader = new InputStreamReader(in);
        } else {
            reader = new InputStreamReader(in, encoding);
        }
        StringWriter sw = new StringWriter();
        copy(reader, sw);
        return sw.toString();
    }

    /**
     * get the data from inputstream as bytes
     * 
     * @param in input stream
     * @return the data of an input stream
     * @throws IOException
     */
    public static byte[] toBytes(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        copy(in, out);
        return out.toByteArray();
    }

    /**
     * get the content from an {@link InputStream} as a list of strings
     * 
     * @param in InputStream
     * @param encoding encoding
     * @return a list of strings
     * @throws IOException
     */
    public static List<String> readLines(InputStream in, String encoding) throws IOException {
        InputStreamReader reader = null;
        if (encoding == null) {
            reader = new InputStreamReader(in);
        } else {
            reader = new InputStreamReader(in, encoding);
        }
        BufferedReader br = new BufferedReader(reader);
        List<String> lines = new ArrayList<String>();
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }

    // copy data from a reader to a writer
    private static long copy(Reader r, Writer w) throws IOException {
        char[] buffer = new char[DEFAULT_BUFFER_SIZE];
        int n = 0;
        long count = 0;
        while ((n = r.read(buffer)) != -1) {
            w.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    // copy data from a input stream to a output stream
    private static long copy(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int n = 0;
        long count = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

}
