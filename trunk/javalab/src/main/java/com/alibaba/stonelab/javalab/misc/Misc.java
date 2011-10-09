/*
 * Copyright 1999-2011 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.misc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.mysql.jdbc.Driver;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-1-26
 */
public class Misc {

    public static void main(String[] args) throws Exception {
        DriverManager.registerDriver(new Driver());
        final Connection con = DriverManager.getConnection("jdbc:mysql://10.20.131.207/alibaba_party", "party",
                                                           "hello1234");
        PreparedStatement ps = con.prepareStatement("id = ? and id = ? and id in (?) and 1 = 1");
        ps.setString(1, "2");
        ps.setString(2, "2");
        ps.setString(3, "2");
        System.out.println(((com.mysql.jdbc.PreparedStatement) ps).toString());
        ps.close();

        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(new DataSource() {

            @Override
            public <T> T unwrap(Class<T> iface) throws SQLException {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public boolean isWrapperFor(Class<?> iface) throws SQLException {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void setLoginTimeout(int seconds) throws SQLException {
                // TODO Auto-generated method stub

            }

            @Override
            public void setLogWriter(PrintWriter out) throws SQLException {
                // TODO Auto-generated method stub

            }

            @Override
            public int getLoginTimeout() throws SQLException {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public PrintWriter getLogWriter() throws SQLException {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Connection getConnection(String username, String password) throws SQLException {
                return con;
            }

            @Override
            public Connection getConnection() throws SQLException {
                // TODO Auto-generated method stub
                return con;
            }
        });
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("ids", Arrays.asList(new Integer[] { 1, 2, 3, 4 }));
        List<Map<String, Object>> results = (List<Map<String, Object>>) template.queryForList("select * from user where id in (:ids)",
                                                                                              param);
        System.out.println(results);
    }
}
