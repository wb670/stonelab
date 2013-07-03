/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.misc.dubbo;

import com.alibaba.dubbo.container.Main;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2013-7-2
 */
public class HelloMain {

    public static void main(String[] args) {
        System.setProperty("dubbo.spring.config", "misc/dubbo.xml");
        Main.main(null);

    }

}
