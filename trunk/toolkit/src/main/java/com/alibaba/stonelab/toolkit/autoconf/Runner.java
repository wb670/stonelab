/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.autoconf;

import java.util.Arrays;

/**
 * @author li.jinl 2010-7-13 下午03:09:57
 */
public class Runner {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Args Error.");
            System.exit(1);
        }

        Autoconf at = new Autoconf(Arrays.asList(args));
        at.validate();
    }

}
