/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.misc.hibernate;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import com.alibaba.stonelab.javalab.misc.hibernate.query.QNotice;
import com.mysema.query.sql.MySQLTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLQueryImpl;
import com.mysema.query.types.Predicate;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> Sep 16, 2011
 */
@SuppressWarnings("deprecation")
public class QueryDslRunner {

    public static void main(String[] args) throws Exception {
        Predicate where = QNotice.notice.id.eq(1).and(QNotice.notice.id.eq(1).or(QNotice.notice.id.eq(1)));

        Configuration cfg = new Configuration();
        cfg.configure("misc/cfg.xml");
        Session session = cfg.buildSessionFactory().openSession();

        SQLQuery query = new SQLQueryImpl(session.connection(), new MySQLTemplates());
        Object r = query.from(QNotice.notice).where(where).list(QNotice.notice.id);
        System.out.println(r);

        session.connection().close();
    }

}
