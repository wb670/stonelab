/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.springmvc.common;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2010-12-17
 */
public class JsonViewResolver implements ViewResolver {

    private static final String DEFAULT_JSON_VIEW_NAME = "json";

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        if (!canResolver(viewName)) {
            return null;
        }
        return new JsonView();
    }

    protected boolean canResolver(String viewName) {
        return DEFAULT_JSON_VIEW_NAME.equals(viewName);
    }

}
