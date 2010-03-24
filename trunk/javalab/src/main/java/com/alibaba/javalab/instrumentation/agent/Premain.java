/**
 * Function: 
 * 
 * File Created at 2010-3-14
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.instrumentation.agent;

import java.lang.instrument.Instrumentation;

/**
 * @author li.jinl
 */
public class Premain {

    public static void premain(String agent, Instrumentation instrumentation) {
        instrumentation.addTransformer(new ModelTransformer(agent));
    }
}
