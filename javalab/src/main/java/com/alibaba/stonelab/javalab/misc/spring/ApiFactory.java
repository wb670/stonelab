/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.misc.spring;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2013-10-8
 */
public class ApiFactory implements FactoryBean {

    @Override
    public Object getObject() throws Exception {
        return new Target();
    }

    @Override
    public Class<?> getObjectType() {
        return Api.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
