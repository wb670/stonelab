/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.misc.hibernate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import com.alibaba.stonelab.javalab.misc.hibernate.query.QNotice;
import com.mysema.query.sql.MySQLTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLQueryImpl;
import com.mysema.query.types.Predicate;
import com.mysql.jdbc.Driver;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> Sep 16, 2011
 */
public class QueryDslRunner {

    public static void main(String[] args) throws Exception {
        Connection con = openConnection();

        QNotice notice = QNotice.notice;
        Predicate where = notice.id.eq(1).and(notice.id.eq(1).or(notice.id.eq(1)));

        SQLQuery query = new SQLQueryImpl(con, new MySQLTemplates());
        query.from(notice);
        query.where(where);
        List<Integer> r = query.list(notice.id);
        System.out.println(r);

        con.close();
    }

    public static Connection openConnection() throws Exception {
        DriverManager.registerDriver(new Driver());
        return DriverManager.getConnection("jdbc:mysql://10.20.156.88:3306/party", "party", "123456");
    }

}
