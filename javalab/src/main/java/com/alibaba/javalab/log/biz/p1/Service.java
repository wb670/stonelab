/**
 * Function: 
 * 
 * File Created at 2010-2-2
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.log.biz.p1;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.javalab.log.biz.BizRandom;

/**
 * TODO Comment of Service
 * 
 * @author li.jinl
 */
public class Service {

    private static final Log LOG = LogFactory.getLog("p1");

    private Dao              dao = new Dao();

    public void logic(String memberId) {
        //参数不对，记录业务日志
        if (!isParameterValid()) {
            LOG.info("Service ERROR: memberId is null.memberId:" + memberId);
            return;
        }
        //是否在白名单，在则记录成功--特殊的成功
        if (isInWhitelist()) {
            LOG.info("success,member is in white list.member=" + memberId);
            return;
        }

        List<String> list = dao.getData(memberId);
        if (list == null) {
            LOG.info("DAO ERROR:member_id is " + memberId);
        } else {
            LOG.info("success.");
        }
        return;
    }

    private boolean isParameterValid() {
        return BizRandom.isSuccessful();
    }

    private boolean isInWhitelist() {
        return BizRandom.isSuccessful();
    }
}
