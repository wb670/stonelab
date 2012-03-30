/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.misc.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.alibaba.stonelab.javalab.misc.hibernate.model.Notice;

/**
 * @author stone 2011-6-28 上午11:14:02
 */
public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("entity");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Notice n = em.find(Notice.class, 1);
        System.out.println(n);
        n.setTitle("关于做好2009年度党内双评工作的意见.");
        em.persist(n);

        em.getTransaction().commit();
        em.close();
    }
}
