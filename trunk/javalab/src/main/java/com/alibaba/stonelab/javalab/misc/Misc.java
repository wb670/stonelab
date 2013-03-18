/*
 * Copyright 1999-2011 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.misc;

import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-1-26
 */
public class Misc {

	public static void main(String[] args) throws Exception {
		System.out.println(URLEncoder.encode("设备A", "UTF-8"));
	}

}
