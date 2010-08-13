/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.learning.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.SimpleNode;

/**
 * <pre>
 * 正常:1+2+3+4
 * 异常:@java.lang.System@getProperties()
 * 异常:@java.lang.Runtime@getRuntime().exit(1)
 * </pre>
 * 
 * @author Stone.J 2010-8-12 上午10:23:33
 */
public class Calc {

    private static final int PORT = 9999;

    private ServerSocket     ss;

    public static void main(String[] args) throws Exception {
        new Calc();
    }

    public Calc() {
        init();
        listen();
    }

    public void init() {
        try {
            ss = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        while (true) {
            try {
                Socket socket = ss.accept();
                new Thread(new Work(socket)).run();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void exit() {
        action("exit");
    }

    private void action(String msg) {
        if (!msg.equals("exit")) {
            System.out.println(msg);
        } else {
            System.exit(1);
        }
    }

    public class Work implements Runnable {

        private static final String ENCODING = "GBK";

        private Socket              socket;
        private BufferedReader      reader;

        public Work(Socket socket) {
            this.socket = socket;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), ENCODING));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                String msg = reader.readLine();
                Object value = calc(msg);
                socket.getOutputStream().write((String.valueOf(value) + "\r\n").getBytes());
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private Object calc(String exp) {
            try {
                OgnlContext ctx = new OgnlContext();
                SimpleNode node = (SimpleNode) Ognl.parseExpression(exp);
                return node.getValue(ctx, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
    }

}
