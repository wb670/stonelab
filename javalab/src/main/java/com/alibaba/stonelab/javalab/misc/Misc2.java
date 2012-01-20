/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.misc;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mysql.jdbc.Driver;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> Nov 15, 2011
 */
public class Misc2 {

    public static void main(String[] args) throws Exception {
        BufferedWriter br = new BufferedWriter(
                                               new OutputStreamWriter(
                                                                      new FileOutputStream(
                                                                                           "/home/stone/tmp/member_ids.txt")));

        DriverManager.registerDriver(new Driver());
        Connection con = DriverManager.getConnection("jdbc:mysql://test.ccbu.mysql.alibaba-inc.com:3307/offer3",
                                                     "offer", "offer");
        PreparedStatement ps = con.prepareStatement("select distinct(member_id) from offer_group_rel limit 0,10000");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            br.write(rs.getString(1));
            br.write("\n");
        }

        br.flush();
        br.close();

        rs.close();
        ps.close();
        con.close();
        System.out.println("Completed.");
    }

}
