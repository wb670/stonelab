/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.sqltester;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

/**
 * IbatisSqlTester单元测试
 * 
 * @author Stone.J 2010-8-9 下午04:57:50
 */
public class IbatisSqlTesterTest extends TestCase {

    private static final String CONFIG = "classpath:sqltester/sqlMapConfig.xml";

    private IbatisSqlTester     ibatisSqlTester;

    @Override
    protected void setUp() throws Exception {
        ibatisSqlTester = new IbatisSqlTester();
        ibatisSqlTester.setSqlMapConfig(CONFIG);
    }

    public void testWithoutParam() {
        SqlStatement sql = ibatisSqlTester.test("findWithoutParam");
        assertEquals("select * from sample", sql.getSql());
        assertEquals("select * from sample", sql.toString());
        assertTrue(sql.getParam() == null);
    }

    public void testWithParam() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", 1);
        param.put("name", "stone");
        param.put("types", new String[] { "A", "B", "C", "D" });

        SqlStatement sql = ibatisSqlTester.test("findWithParam", param);
        assertEquals("select * from sample where id = ? and name = ? and type in ( ? , ? , ? , ? )", sql.getSql());
        assertEquals("select * from sample where id = '1' and name = 'stone' and type in ( 'A' , 'B' , 'C' , 'D' )",
                sql.toString());
        assertTrue(sql.getParam().length == 6);
    }

}
