/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.weblab.statistics.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

import com.alibaba.weblab.statistics.service.domain.Party;

/**
 * party service
 * 
 * @author Maurice.J Dec 23, 2009 6:26:16 PM
 */
public class PartyService {

    private JdbcTemplate jdbcTemplate;

    public void addParty(Party party) {
        Assert.notNull(party);
        Assert.hasText(party.getJobNumber());
        Assert.hasText(party.getName());

        //        String query = "select count(*) from party where job_number = ? and name = ?";
        //        int count = jdbcTemplate.queryForInt(query, new Object[] { party.getJobNumber(), party.getName() });
        //        if (count > 0) {
        //            return;
        //        }

        String create = "insert into party (job_number,name) values (?,?)";
        jdbcTemplate.update(create, new Object[] { party.getJobNumber(), party.getName() });

    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
