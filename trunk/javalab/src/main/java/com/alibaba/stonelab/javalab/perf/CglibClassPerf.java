/*
 * Copyright 1999-2011 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.perf;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.core.CodeGenerationException;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-6-15
 */
public class CglibClassPerf {

    private static final int loop = 1000 * 1000; // loop count
    private static int       nc   = 0;          // number count

    public static void main(String[] args) throws Exception {
        cglibNewObject();
        cglibNewObject();
        cglibNewObject();
        cglibNewObject();
    }

    public static void cmd(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("args error.");
            System.exit(-1);
        }
        if (args[0].equals("newObject")) {
            newObject();
            newObject();
        } else if (args[0].equals("classNewObject")) {
            classNewObject();
            classNewObject();
        } else if (args[0].equals("cglibNewObject")) {
            cglibNewObject();
            cglibNewObject();
        } else {
            System.out.println("args error.");
            System.exit(-1);
        }
    }

    public static void newObject() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            nc++;
            Bean b = new Bean();
            b.setId(nc);
            b.setName("name" + nc);
        }
        long dur = System.currentTimeMillis() - start;
        System.out.println("newObject: " + dur);
    }

    public static void classNewObject() throws Exception {
        Class<Bean> cache = Bean.class;
        long start = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            nc++;
            Bean b = cache.newInstance();
            b.setId(nc);
            b.setName("name" + nc);
        }
        long dur = System.currentTimeMillis() - start;
        System.out.println("classNewObject: " + dur);
    }

    public static void cglibNewObject() throws Exception {
        MethodInterceptor callback = new MethodInterceptor() {

            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                return proxy.invokeSuper(obj, args);
            }
        };

        Enhancerx enhancer = new Enhancerx();
        enhancer.setSuperclass(Bean.class);
        enhancer.setCallbackType(callback.getClass());
        Class<Bean> proxyClass = enhancer.createClass();

        long start = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            nc++;
            Enhancerx.registerCallbacks(proxyClass, new Callback[] { callback });
            Bean b = proxyClass.newInstance();
            Enhancerx.registerCallbacks(proxyClass, null);
            b.setId(nc);
            b.setName("name" + nc);
        }
        long dur = System.currentTimeMillis() - start;
        System.out.println("cglibNewObject: " + dur);
    }

    public static class Bean {

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

    public static class Enhancerx extends Enhancer {

        private static Map<Class<?>, Method> cache = new HashMap<Class<?>, Method>();

        public static void registerCallbacks(Class<?> generatedClass, Callback[] callbacks) {
            setCallbacksHelper(generatedClass, callbacks, "CGLIB$SET_THREAD_CALLBACKS");
        }

        private static void setCallbacksHelper(Class<?> type, Callback[] callbacks, String methodName) {
            try {
                if (!cache.containsKey(type)) {
                    cache.put(type, type.getDeclaredMethod(methodName, new Class[] { Callback[].class }));
                }
                Method setter = cache.get(type);
                setter.invoke(null, new Object[] { callbacks });
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException(type + " is not an enhanced class");
            } catch (IllegalAccessException e) {
                throw new CodeGenerationException(e);
            } catch (InvocationTargetException e) {
                throw new CodeGenerationException(e);
            }
        }

    }

}
