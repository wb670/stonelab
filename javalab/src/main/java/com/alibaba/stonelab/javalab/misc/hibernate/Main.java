/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.misc.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.alibaba.stonelab.javalab.misc.hibernate.model.Notice;

/**
 * @author stone 2011-6-28 上午11:14:02
 */
public class Main {

    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.configure("misc/cfg.xml");

        SessionFactory sf = cfg.buildSessionFactory();

        Session s = sf.openSession();
        Notice n = (Notice) s.get(Notice.class, 1);
        System.out.println(n);
    }
}
