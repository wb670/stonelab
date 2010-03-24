/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.javalab.napoli;

import com.alibaba.china.rialto.server.RialtoServer;


/**
 * @author Maurice.J Dec 2, 2009 4:21:19 PM
 */
public class RialtoRun {

    private static final String       SERVICE       = "/classpath/com/alibaba/javalab/napoli/service.xml";
    private static final String       LOG4J         = "/classpath/log4j.xml";
    private static final String       RIALTO        = "/classpath/com/alibaba/javalab/napoli/rialto.xml";

    private static final RialtoServer RIALTO_SERVER = new RialtoServer();

    public static void main(String[] args) {
        RIALTO_SERVER.start(SERVICE, LOG4J, RIALTO);
    }

}
