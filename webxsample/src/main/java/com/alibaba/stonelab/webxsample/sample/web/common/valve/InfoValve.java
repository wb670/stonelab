/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.stonelab.webxsample.sample.web.common.valve;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.service.pipeline.support.AbstractValve;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.alibaba.citrus.turbine.util.TurbineUtil;

/**
 * @author Stone.J 2010-8-16 ÏÂÎç12:36:00
 */
public class InfoValve extends AbstractValve {

    private static final Logger LOGGER = LoggerFactory.getLogger(InfoValve.class);

    @Autowired
    private HttpServletRequest  request;

    @Override
    public void invoke(PipelineContext ctx) throws Exception {
        TurbineRunData rundata = TurbineUtil.getTurbineRunData(request);
        LOGGER.info(rundata.getTarget());

        ctx.invokeNext();
    }
}
