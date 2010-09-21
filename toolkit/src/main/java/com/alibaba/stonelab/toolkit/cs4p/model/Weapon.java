/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.cs4p.model;

/**
 * @author Stone.J 2010-9-17 下午01:10:41
 */
public interface Weapon {

    /**
     * 开火
     * 
     * @param p 瞄准位置
     * @return 实际位置
     */
    public Area onfire(Point p);

}
