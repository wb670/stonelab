/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.misc.asc;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.china.searchengine.dal.dao.OfferSearch;
import com.alibaba.china.searchengine.dal.dao.dto.OfferSearchResult;
import com.alibaba.china.searchengine.dal.dao.param.OfferParam;

/**
 * offer search sample
 * 
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> Aug 26, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:misc/asc/asc.xml")
public class AscSample {

    @Autowired
    private OfferSearch offerSearch;

    @Test
    public void testInit() {
        Assert.assertNotNull(offerSearch);
    }

    @Test
    public void testOfferSearch() {
        OfferParam op = new OfferParam();
        op.setMemberids(new String[] { "athena2002" });
        op.setDetail(false);
        OfferSearchResult result = offerSearch.getOffer(op);
        System.out.println(result);
        System.out.println(result.getResultObject());
    }
}
