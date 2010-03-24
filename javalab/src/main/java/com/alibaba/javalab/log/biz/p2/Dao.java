/**
 * Function: 
 * 
 * File Created at 2010-2-2
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.log.biz.p2;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.javalab.log.biz.BizRandom;

/**
 * TODO Comment of Dao
 * 
 * @author li.jinl
 */
public class Dao {

    public List<String> getData(String memberId) {
        if (!isParameterValid()) {
            return null;
        }
        return new ArrayList<String>();
    }

    private boolean isParameterValid() {
        return BizRandom.isSuccessful();
    }

}
