/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.misc.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.alibaba.stonelab.javalab.misc.hibernate.model.Notice;

/**
 * @author stone 2011-6-28 上午11:14:02
 */
public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("entity");
        EntityManager em = emf.createEntityManager();

        CriteriaBuilder builder = emf.getCriteriaBuilder();
        CriteriaQuery<Notice> query = builder.createQuery(Notice.class);
        Root<Notice> root = query.from(Notice.class);

        query.select(root);
        query.where(builder.equal(builder.currentDate(), root.get("gmt_create")));

        List<Notice> list = em.createQuery(query).getResultList();
        System.out.println(list);

        em.close();
        emf.close();
    }
}
