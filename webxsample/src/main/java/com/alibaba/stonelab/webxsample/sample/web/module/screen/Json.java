/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.stonelab.webxsample.sample.web.module.screen;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.stonelab.webxsample.sample.web.common.valve.PerformJsonValve;

/**
 * @author Stone.J 2010-8-17 ÏÂÎç12:31:26
 */
public class Json {

    public void execute(@Param("id") String id, @Param("name") String name, Context context) {
        Map<String, String> json = new HashMap<String, String>();
        json.put("id", id);
        json.put("name", name);
        context.put(PerformJsonValve.KEY_JSON_DATA, json);
    }

}
