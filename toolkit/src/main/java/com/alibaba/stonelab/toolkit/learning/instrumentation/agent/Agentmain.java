/**
 * Function: File Created at 2010-3-17 $Id$ Copyright 2009 Alibaba.com Croporation Limited. All rights reserved.
 */
package com.alibaba.stonelab.toolkit.learning.instrumentation.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.HashSet;
import java.util.Set;

import com.alibaba.stonelab.toolkit.learning.instrumentation.Model;

/**
 * @author li.jinl
 */
public class Agentmain {

    private static final Set<ClassFileTransformer> ctx = new HashSet<ClassFileTransformer>();

    public static void agentmain(String agent, Instrumentation inst) throws ClassNotFoundException,
                                                                    UnmodifiableClassException, InterruptedException {

        String[] args = agent.split(" ");
        String cmd = args[0];
        String model = args[1];

        System.out.println(cmd + ":" + model);

        if ("start".equals(cmd)) {
            ClassFileTransformer transformer = new ModelTransformer(model);
            ctx.add(transformer);
            inst.addTransformer(transformer, true);
            inst.retransformClasses(Model.class);
            System.out.println("start");
        }

        if ("stop".equals(cmd)) {
            for (ClassFileTransformer transformer : ctx) {
                inst.removeTransformer(transformer);
            }
            System.out.println("stop");
        }

    }
}
