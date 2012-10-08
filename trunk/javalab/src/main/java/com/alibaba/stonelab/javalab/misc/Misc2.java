/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.misc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> Nov 15, 2011
 */
public class Misc2 {

	public static void main(String[] args) throws Exception {
		Log LOG = LogFactory.getLog(Misc2.class);

		MDC.put("ip", "127.0.0.1");
		MDC.put("user", "stone2083");

		LOG.error("OK");
		MDC.remove("ip");
		MDC.remove("user");
	}

}
