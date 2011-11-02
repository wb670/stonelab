/*
 * Copyright 1999-2011 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.misc;

import java.util.Calendar;
import java.util.Date;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-1-26
 */
public class Misc {

    public static void main(String[] args) {
        Calendar s = Calendar.getInstance();
        s.set(2008, 8, 18, 0, 0, 0);
        System.out.println(s.getTime());
    }

}
