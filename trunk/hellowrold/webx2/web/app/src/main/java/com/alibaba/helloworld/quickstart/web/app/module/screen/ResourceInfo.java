/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.helloworld.quickstart.web.app.module.screen;

import com.alibaba.service.resource.Resource;
import com.alibaba.service.resource.ResourceLoaderService;
import com.alibaba.service.resource.ResourceNotFoundException;
import com.alibaba.service.template.TemplateContext;
import com.alibaba.turbine.module.screen.TemplateScreen;
import com.alibaba.turbine.service.rundata.RunData;
import com.alibaba.webx.WebxException;

/**
 * @author Stone.J 2010-8-18 ÏÂÎç05:49:36
 */
public abstract class ResourceInfo extends TemplateScreen {

    public abstract ResourceLoaderService getResourceLoaderService();

    @Override
    protected void execute(RunData rundata, TemplateContext context) throws WebxException {
        String res = rundata.getParameters().getString("res");
        ResourceLoaderService resourceLoaderService = getResourceLoaderService();
        Resource resource;
        try {
            resource = resourceLoaderService.getResource(res);
        } catch (ResourceNotFoundException e) {
            throw new WebxException();
        }
        context.put("res", resource);
    }

}
