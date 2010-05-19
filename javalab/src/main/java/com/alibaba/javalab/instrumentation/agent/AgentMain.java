/**
 * Function: 
 * 
 * File Created at 2010-3-17
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.instrumentation.agent;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

import com.alibaba.javalab.instrumentation.Model;

/**
 * @author li.jinl
 */
public class Agentmain {

    public static void agentmain(String agent, Instrumentation inst) throws ClassNotFoundException,
            UnmodifiableClassException, InterruptedException {
        ModelTransformer transformer = new ModelTransformer(agent);
        inst.addTransformer(transformer, true);
        inst.retransformClasses(Model.class);
        inst.removeTransformer(transformer);
    }
}
