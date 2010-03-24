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
public class AgentMain {

    public static void agentmain(String agent, Instrumentation inst) throws ClassNotFoundException,
            UnmodifiableClassException, InterruptedException {
        inst.addTransformer(new ModelTransformer(agent));
        inst.retransformClasses(Model.class);
    }

}
