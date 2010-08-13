/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.learning.examples;

import java.util.HashMap;
import java.util.Map;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.SimpleNode;

/**
 * @author Stone.J 2010-8-6 下午08:58:48
 */
public class OgnlSample {

    public static void main(String[] args) throws Exception {
        OgnlContext ctx = new OgnlContext();

        Map<String, Object> root = new HashMap<String, Object>();
        root.put("name", "12345");
        root.put("map", new HashMap<String, String>() {

            private static final long serialVersionUID = 1L;

            {
                put("1", "1");
                put("12", "1");
            }
        });

        SimpleNode node = null;
        node = (SimpleNode) Ognl.parseExpression("('#result'(#result=1*2+3))");
        System.out.println(node.getValue(ctx, "result1"));

        node = (SimpleNode) Ognl.parseExpression("@java.lang.System@out.println(1000)");
        node.getValue(ctx, null);

        node = (SimpleNode) Ognl.parseExpression("@java.lang.Runtime@getRuntime().exit(0)");
        node.getValue(ctx, null);

        System.out.println("here");
    }

    public static class User {

        private int    id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}
