/*
 * Copyright 1999-2011 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.webx2sample.web.module.screen;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.service.template.TemplateContext;
import com.alibaba.turbine.module.screen.TemplateScreen;
import com.alibaba.turbine.service.rundata.RunData;
import com.alibaba.webx.WebxException;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-2-9
 */
public class Index extends TemplateScreen {

    @Override
    protected void execute(RunData rundata, TemplateContext context) throws WebxException {
        HttpServletResponse res = rundata.getResponse();
        res.setStatus(404);
    }

}
