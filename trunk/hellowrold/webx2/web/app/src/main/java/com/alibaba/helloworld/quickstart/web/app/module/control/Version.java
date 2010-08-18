/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.helloworld.quickstart.web.app.module.control;

import com.alibaba.service.template.TemplateContext;
import com.alibaba.turbine.module.control.TemplateControl;
import com.alibaba.turbine.service.rundata.RunData;
import com.alibaba.webx.WebxException;

/**
 * @author Stone.J 2010-8-18 ÉÏÎç10:13:49
 */
public class Version extends TemplateControl {

    private static final String PARAM_VERSION = "version";

    private static final String VERSION       = "HelloWorld-Webx2.0-1.0.0";

    @Override
    protected void execute(RunData rundata, TemplateContext context) throws WebxException {
        context.put(PARAM_VERSION, VERSION);
    }

}
