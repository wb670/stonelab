/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.cs4p.model;

import java.util.Collection;

/**
 * 战场地图
 * 
 * @author Stone.J 2010-9-17 下午01:08:34
 */
public interface Battlefield {

    /**
     * 添加一个队员
     * 
     * @param p 指定地图所在位置
     */
    public void addMember(Member m);

    /**
     * 清除一个队员
     * 
     * @param m 队员
     */
    public void removeMember(Member m);

    /**
     * 得到所有队员
     * 
     * @return
     */
    public Collection<Member> getMembers();

    /**
     * 得到中弹队员
     * 
     * @return
     */
    public Collection<Member> getFiredMembers(Area area);

}
