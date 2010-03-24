/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.javalab.mapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.javalab.mapper.model.Pojo;
import com.alibaba.javalab.mapper.model.Version;
import com.alibaba.pivot.common.mapping.util.BeanMappingUtil;

/**
 * @author Maurice.J Nov 18, 2009 11:12:21 AM
 */
public class TestRun {

    private static BeanMappingUtil util = new BeanMappingUtil();

    public static void main(String[] args) {
        pojo2map();
        map2pojo();
    }

    private static void pojo2map() {
        Pojo pojo = getPojo();
        Map<String, String> map = new HashMap<String, String>();
        util.mappingOnFire(pojo, map);
        System.out.println(map);
    }

    private static void map2pojo() {
        Map<String, Object> map = getMap();
        Pojo pojo = new Pojo();
        util.mappingOnFire(map, pojo);
        System.out.println(pojo);
    }

    private static Pojo getPojo() {
        Pojo pojo = new Pojo();
        pojo.setId(1);
        pojo.setName("name");
        Version version = new Version();
        version.setId(1);
        version.setDate(new Date());
        pojo.setVersion(version);
        return pojo;
    }

    private static Map<String, Object> getMap() {
        return new HashMap<String, Object>() {

            private static final long serialVersionUID = -9020342515422952616L;

            {
                put("id", "1");
                put("name", "name");
                Map<String, String> version = new HashMap<String, String>() {

                    private static final long serialVersionUID = -1582331772388906098L;

                    {
                        put("id", "1");
                        put("date", "1258514445758");
                    }
                };
                put("version", version);
            }
        };
    }

}
