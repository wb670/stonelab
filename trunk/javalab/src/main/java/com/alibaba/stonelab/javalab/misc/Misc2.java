/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.misc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import com.mysql.jdbc.Driver;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> Nov 15, 2011
 */
public class Misc2 {

    public static void main(String[] args) throws Exception {
        DriverManager.registerDriver(new Driver());

        Connection con = DriverManager.getConnection("jdbc:mysql://10.20.156.49:3306/test", "root", "123456");
        PreparedStatement ps = con.prepareStatement("select count(*) from user where name > 10000");
        ps.executeQuery();
    }

}
