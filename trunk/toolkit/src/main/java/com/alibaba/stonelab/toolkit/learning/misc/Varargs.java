/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.learning.misc;

/**
 * 可变参数测试
 * 
 * @author Stone.J 2010-9-15 上午09:16:49
 */
public class Varargs {

    public static void main(String[] args) {
        /** 无参情况 */
        test();

        /**
         * <pre>
         * The argument of type null should explicitly be cast to String[] 
         * for the invocation of the varargs method test(String...) from type Varargs. 
         * 
         * It could alternatively be cast to String for a varargs invocation
         * </pre>
         */
        /** 参数为null */
        // test(null);

        /** 指明类型 */
        test((String[]) null);
        test((String) null);
    }

    public static void test(String... params) {
        System.out.println(params);
    }

}
