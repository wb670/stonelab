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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.javalab.log.biz.BizRandom;

/**
 * TODO Comment of Dao
 * 
 * @author li.jinl
 */
public class Dao {

    private static final Log LOG = LogFactory.getLog("p1");

    public List<String> getData(String memberId) {
        //参数不对，记录业务日志
        if (!isParameterValid()) {
            LOG.info("can't find memberId,memberId=" + memberId);
            return null;
        }
        return new ArrayList<String>();
    }

    private boolean isParameterValid() {
        return BizRandom.isSuccessful();
    }

}
