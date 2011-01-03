/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.springmvc.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.servlet.View;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2010-12-17
 */
public class JsonView implements View {

    public static final View          instance          = new JsonView();
    private static final String       JSON_CONTENT_TYPE = "text/json";

    private static final ObjectMapper mapper            = new ObjectMapper();

    @Override
    public String getContentType() {
        return JSON_CONTENT_TYPE;
    }

    @Override
    public void render(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(getContentType());
        if (model == null || model.isEmpty()) {
            return;
        }
        String json = mapper.writeValueAsString(model);
        response.getWriter().write(json);
        response.getWriter().close();
    }
}
