/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.cs4p.model.weapon;

import com.alibaba.stonelab.toolkit.cs4p.model.Area;
import com.alibaba.stonelab.toolkit.cs4p.model.Point;
import com.alibaba.stonelab.toolkit.cs4p.model.Weapon;
import com.alibaba.stonelab.toolkit.cs4p.model.area.SimpleArea;

/**
 * 高级武器,指哪打哪
 * 
 * @author Stone.J 2010-9-17 下午02:40:05
 */
public class SimpleWeapon implements Weapon {

    @Override
    public Area onfire(Point p) {
        return new SimpleArea(p, 0);
    }

}
