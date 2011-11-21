/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.perf;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

import com.alibaba.simpleEL.Expr;
import com.alibaba.simpleEL.eval.DefaultExpressEvalService;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> Nov 16, 2011
 */
public class ElPerf {

    private static final MethodClass   TEST    = new MethodClass();
    private static final int           loop    = 1000000000;

    private static Method              method;
    static {
        try {
            method = MethodClass.class.getDeclaredMethod("value", int.class);
            method.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static FastMethod          fastMethod;
    static {
        FastClass fc = FastClass.create(MethodClass.class);
        fastMethod = fc.getMethod("value", new Class[] { int.class });
    }

    private static Expression          expression;
    private static JexlContext         ctx     = new MapContext();
    static {
        Map<String, Object> funcs = new HashMap<String, Object>();
        funcs.put("TEST", TEST);
        JexlEngine engine = new JexlEngine();
        engine.setFunctions(funcs);
        engine.setCache(10);
        expression = engine.createExpression("TEST:value(value)");

        ctx.set("value", 3);
    }

    private static Expr                simpleEl;
    private static Map<String, Object> context = new HashMap<String, Object>();
    static {
        DefaultExpressEvalService service = new DefaultExpressEvalService();
        service.regsiterVariant(MethodClass.class, "TEST");
        service.regsiterVariant(int.class, "value");
        context.put("TEST", TEST);
        context.put("value", 4);
        try {
            simpleEl = service.getExpr(context, "@TEST.value(@value)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(method.invoke(TEST, 1));
        System.out.println(fastMethod.invoke(TEST, new Object[] { 2 }));
        System.out.println(expression.evaluate(ctx));
        System.out.println(simpleEl.eval(context));

        for (int i = 0; i < 20000; i++) {
            method.invoke(TEST, i);
            fastMethod.invoke(TEST, new Object[] { i });
            expression.evaluate(ctx);
            simpleEl.eval(context);
        }

        loop();
        loopFastMethod();
        loopMethod();
        loopJexl();
        loopSimpleEl();
    }

    public static void loop() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            TEST.value(i);
        }
        long dur = System.currentTimeMillis() - start;
        System.out.println("loopMethod:\t\t" + dur);
    }

    public static void loopMethod() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            method.invoke(TEST, i);
        }
        long dur = System.currentTimeMillis() - start;
        System.out.println("loopMethod:\t\t" + dur);
    }

    public static void loopFastMethod() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            fastMethod.invoke(TEST, new Object[] { i });
        }
        long dur = System.currentTimeMillis() - start;
        System.out.println("loopFastMethod:\t\t" + dur);
    }

    public static void loopJexl() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            ctx.set("value", i);
            expression.evaluate(ctx);
        }
        long dur = System.currentTimeMillis() - start;
        System.out.println("loopJexl:\t\t" + dur);
    }

    public static void loopSimpleEl() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            context.put("value", i);
            simpleEl.eval(context);
        }
        long dur = System.currentTimeMillis() - start;
        System.out.println("loopSimpleEl:\t\t" + dur);
    }

    public static final class MethodClass {

        public int value(int value) {
            return value;
        }

    }
}
