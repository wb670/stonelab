/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.learning.cglib;

import net.sf.cglib.proxy.Enhancer;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2010-10-29
 */
public class CglibMain {

    public static void main(String[] args) throws Exception {
        TargetBean b = new TargetBean();
        b.hello("hello");

        Proxy p = new Proxy();
        Enhancer e = new Enhancer();
        e.setSuperclass(TargetBean.class);
        e.setCallback(p);

        TargetBean eb = (TargetBean) e.create();
        eb.hello("hello");

    }

}
