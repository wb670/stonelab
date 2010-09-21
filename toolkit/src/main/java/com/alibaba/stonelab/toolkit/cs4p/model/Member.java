/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.cs4p.model;

import java.util.Collection;

/**
 * 队员
 * 
 * @author Stone.J 2010-9-17 下午01:07:56
 */
public interface Member {

    /**
     * 得到队员信息
     * 
     * @return
     */
    public Info getInfo();

    /**
     * 添加一个队员
     */
    public void add(Point point);

    /**
     * 移动
     * 
     * @param p
     */
    public void move(Point p);

    /**
     * 删除一个队员
     */
    public void remove();

    /**
     * 得到当前所在位置点
     * 
     * @return
     */
    public Point getPoint();

    /**
     * 得到当前所在位置
     * 
     * @return
     */
    public Area getLocation();

    /**
     * 开火
     * 
     * @param p 瞄准位置
     */
    public Collection<Member> fire(Point p);

}
