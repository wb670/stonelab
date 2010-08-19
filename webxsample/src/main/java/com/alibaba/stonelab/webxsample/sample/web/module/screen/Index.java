/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.stonelab.webxsample.sample.web.module.screen;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.form.FormService;
import com.alibaba.citrus.service.form.Group;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.stonelab.webxsample.sample.service.domain.Sample;
import com.alibaba.stonelab.webxsample.sample.service.impl.SampleService;

/**
 * @author Stone.J 2010-8-19 ÏÂÎç05:04:11
 */
public class Index {

    @Autowired
    private FormService   formService;
    @Autowired
    private SampleService sampleService;

    public void execute(Context ctx) {
        Group group = formService.getForm().getGroup("sample");
        Sample sample = sampleService.getSample();
        group.mapTo(sample);
        ctx.put("group", group);
    }

}
