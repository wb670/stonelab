/**
 * Function: 
 * 
 * File Created at 2010-3-17
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.stonelab.toolkit.learning.instrumentation;

import com.sun.tools.attach.VirtualMachine;

/**
 * @author li.jinl
 */
public class Attach {

    private static final String BASE  = "D:/tmp/";
    private static final String AGENT = "agent.jar";
    private static final String MODEL = "Model";

    public static void main(String[] args) throws Exception {
        VirtualMachine vm = VirtualMachine.attach("2300");
        vm.loadAgent(BASE + AGENT, BASE + MODEL);
        vm.detach();
    }

}
