/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.webxsample.sample.web.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.stonelab.webxsample.sample.web.common.dataresolver.Json;

/**
 * @author Stone.J 2010-9-15 ÏÂÎç06:36:53
 */
public class JsonIn {

    public void execute(@Json(name = "json") JsonBean jsonBean, @Json(name = "json1") JsonBean jsonBean2,
                        Context context) {
        context.put("json", jsonBean);
        context.put("json2", jsonBean2);
    }

    public static class JsonBean {

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
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
            return "JsonBean [id=" + id + ", name=" + name + "]";
        }

    }

}
