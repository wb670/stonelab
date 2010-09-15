/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.learning.perf;

import java.lang.reflect.Method;

import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

/**
 * <pre>
 * 直接调用(LOOP=1亿):       235MS 
 * 反射调用(LOOP=1亿):       29188MS
 * 反射调用(优化)(LOOP=1亿):  5672MS
 * 放射调用(CGLIB)(LOOP=1亿):5390MS
 * </pre>
 * 
 * @author Stone.J 2010-9-15 上午10:07:27
 */
public class ReflectionTest {

    private static final int                      DEFAULT_INT                = 1;
    private static final Integer                  DEFAULT_INTEGER            = 1;
    private static final String                   DEFAULT_STRING             = "name";
    private static final Object[]                 DEFAULT_INTS               = { 1 };
    private static final Object[]                 DEFAULT_INTEGERS           = new Integer[] { 1 };
    private static final Object[]                 DEFAULT_STRINGS            = new String[] { "name" };

    private static final Bean                     BEAN                       = new Bean();

    private static final CachedMethod             CACHED_METHOD              = new CachedMethod();
    private static final OptimizationCachedMethod OPTIMIZATION_CACHED_METHOD = new OptimizationCachedMethod();
    private static final CglibCachedMethod        CGLIB_CACHED_METHOD        = new CglibCachedMethod();

    private static final int                      LOOP                       = 1 * 10000 * 10000;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("args error.");
            System.exit(1);
        }
        int tc = Integer.valueOf(args[0]);

        long start = System.currentTimeMillis();
        for (int i = 0; i < LOOP; i++) {
            switch (tc) {
                case 1:
                    // 直接调用
                    test();
                    break;
                case 2:
                    // 反射调用
                    testReflection();
                    break;
                case 3:
                    // 优化后反射调用
                    testOptimizationReflection();
                    break;
                case 4:
                    // cglib反射调用
                    testCglibReflection();
                    break;
                default:
                    System.out.println("tc error. must be [1-4]");
                    break;
            }
        }
        long dur = System.currentTimeMillis() - start;
        System.out.println(dur);
    }

    // 直接调用测试
    public static void test() {
        BEAN.setId(DEFAULT_INT);
        BEAN.setCode(DEFAULT_INTEGER);
        BEAN.setName(DEFAULT_STRING);
    }

    // 反射调用测试
    public static void testReflection() {
        try {
            CACHED_METHOD.SET_ID.invoke(BEAN, DEFAULT_INTS);
            CACHED_METHOD.SET_CODE.invoke(BEAN, DEFAULT_INTEGERS);
            CACHED_METHOD.SET_NAME.invoke(BEAN, DEFAULT_STRINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 优化后反射调用测试
    public static void testOptimizationReflection() {
        try {
            OPTIMIZATION_CACHED_METHOD.SET_ID.invoke(BEAN, DEFAULT_INTS);
            OPTIMIZATION_CACHED_METHOD.SET_CODE.invoke(BEAN, DEFAULT_INTEGERS);
            OPTIMIZATION_CACHED_METHOD.SET_NAME.invoke(BEAN, DEFAULT_STRINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // cglib反射调用测试
    public static void testCglibReflection() {
        try {
            CGLIB_CACHED_METHOD.cglibSetId.invoke(BEAN, DEFAULT_INTS);
            CGLIB_CACHED_METHOD.cglibSetCode.invoke(BEAN, DEFAULT_INTEGERS);
            CGLIB_CACHED_METHOD.cglibSetName.invoke(BEAN, DEFAULT_STRINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <pre>
     * 测试的bean
     * 简单的int Integer String类型
     * </pre>
     * 
     * @author Stone.J 2010-9-15 上午10:40:40
     */
    public static class Bean {

        private int     id;
        private Integer code;
        private String  name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    /**
     * 反射测试需要:Cached Method
     * 
     * @author Stone.J 2010-9-15 上午10:41:04
     */
    public static class CachedMethod {

        public Method SET_ID;
        public Method SET_CODE;
        public Method SET_NAME;

        {
            try {
                SET_ID = Bean.class.getDeclaredMethod("setId", int.class);
                SET_CODE = Bean.class.getDeclaredMethod("setCode", Integer.class);
                SET_NAME = Bean.class.getDeclaredMethod("setName", String.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 反射测试需要:优化后的Cached Method
     * 
     * @author Stone.J 2010-9-15 上午10:41:21
     */
    public static class OptimizationCachedMethod extends CachedMethod {

        {
            /** 所谓的优化 */
            SET_ID.setAccessible(true);
            SET_CODE.setAccessible(true);
            SET_NAME.setAccessible(true);
        }

    }

    /**
     * 反射测试需要,使用cglib的fast method
     * 
     * @author Stone.J 2010-9-15 上午10:51:53
     */
    public static class CglibCachedMethod extends CachedMethod {

        public FastMethod cglibSetId;
        public FastMethod cglibSetCode;
        public FastMethod cglibSetName;

        private FastClass cglibBeanClass = FastClass.create(Bean.class);

        {
            cglibSetId = cglibBeanClass.getMethod(SET_ID);
            cglibSetCode = cglibBeanClass.getMethod(SET_CODE);
            cglibSetName = cglibBeanClass.getMethod(SET_NAME);
        }

    }

}
