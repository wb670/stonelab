/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.cs4p.model;


/**
 * @author Stone.J 2010-9-17 下午06:39:37
 */
public interface Area {

    /**
     * 两个区域是否相交
     * 
     * @param area
     * @return
     */
    public boolean isCross(Area area);

}
