/*
 * Copyright 1999-2011 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.webxsample.sample.web.module.action.examples;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.citrus.service.form.Group;
import com.alibaba.citrus.turbine.dataresolver.FormGroups;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-2-17
 */
public class GroupsAction {

    public void doUpldate(@FormGroups("user") Group[] groups) {
        List<User> users = new ArrayList<User>();
        for (Group group : groups) {
            User user = new User();
            group.setProperties(user);
            users.add(user);
        }
        System.out.println(users);
    }

    public static final class User {

        private String name;
        private String password;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return this.name + ":" + this.password;
        }

    }

}
