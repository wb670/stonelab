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

import com.sun.tools.attach.VirtualMachine;

/**
 * @author li.jinl
 */
public class AgentRun {

    public static void main(String[] args) throws Exception {
        VirtualMachine vm = VirtualMachine.attach("4956");
        vm.loadAgent("D:/tmp/agent/agent.jar");
        vm.detach();
    }

}
