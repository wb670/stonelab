/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.helloworld.quickstart.web.app.module.screen;

import com.alibaba.common.lang.StringUtil;
import com.alibaba.service.template.TemplateContext;
import com.alibaba.turbine.module.screen.TemplateScreen;
import com.alibaba.turbine.service.rundata.RunData;
import com.alibaba.webx.WebxException;

/**
 * @author Stone.J 2010-8-17 ÏÂÎç03:42:19
 */
public class Session extends TemplateScreen {

    private static final String PARAM_SESSION_INFO      = "info";
    private static final String PARAM_SESSION_USER_ID   = "id";
    private static final String PARAM_SESSION_USER_NAME = "name";

    private static final String KEY_SESSION_INFO        = "info";
    private static final String KEY_SESSION_USER        = "user";

    @Override
    protected void execute(RunData rundata, TemplateContext context) throws WebxException {
        String info = rundata.getParameters().getString(PARAM_SESSION_INFO);
        int id = rundata.getParameters().getInt(PARAM_SESSION_USER_ID);
        String name = rundata.getParameters().getString(PARAM_SESSION_USER_NAME);

        //ÉèÖÃinfo
        if (StringUtil.isNotEmpty(info)) {
            rundata.getSession().setAttribute(KEY_SESSION_INFO, info);
        }
        //ÉèÖÃuser
        else if (id > 0) {
            User user = new User(id, name);
            rundata.getSession().setAttribute(KEY_SESSION_USER, user);
        }

    }

    public static class User {

        private int    id;
        private String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User [id=" + id + ", name=" + name + "]";
        }

    }

}
