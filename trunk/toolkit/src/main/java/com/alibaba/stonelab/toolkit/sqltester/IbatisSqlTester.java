/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.sqltester;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.util.ResourceUtils;

import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;
import com.ibatis.sqlmap.engine.mapping.sql.Sql;
import com.ibatis.sqlmap.engine.mapping.statement.MappedStatement;
import com.ibatis.sqlmap.engine.scope.RequestScope;

/**
 * <pre>
 * IBtatis SQL 测试
 * 一般IBatis DAO单元测试,主要就是在测试ibatis的配置文件.
 * IbatisSqlTester将根据提供的Sql Map Id 和 对应的参数,返回 {@link SqlStatement}对象,提供最终执行的sql语句
 * 通过外部SqlAssert对象,将预计Sql和实际产生的Sql进行对比,判断是否正确
 * </pre>
 * 
 * @author Stone.J 2010-8-9 下午02:58:46
 */
public class IbatisSqlTester {

    // sqlMapClient
    private ExtendedSqlMapClient sqlMapClient;

    /**
     * 根据提供的SqlMap ID,得到 {@link SqlStatement}对象
     * 
     * @param sqlId: sql map id
     * @return @see {@link SqlStatement}
     */
    public SqlStatement test(String sqlId) {
        //得到MappedStatement对象
        MappedStatement ms = sqlMapClient.getMappedStatement(sqlId);
        if (ms == null) {
            //TODO:建议封转自己的异常对象
            throw new RuntimeException("can't find MappedStatement.");
        }

        //按照Ibatis代码,得到Sql和Param信息
        RequestScope request = new RequestScope();
        ms.initRequest(request);
        Sql sql = ms.getSql();
        String sqlValue = sql.getSql(request, null);

        //组转返回对象
        SqlStatement ret = new SqlStatement();
        ret.setSql(sqlValue);
        return ret;
    }

    /**
     * 根据提供的SqlMap ID和对应的param信息,得到 {@link SqlStatement}对象
     * 
     * @param sqlId: sql map id
     * @param param: 参数内容
     * @return @see {@link SqlStatement}
     */
    public SqlStatement test(String sqlId, Object param) {
        //得到MappedStatement对象
        MappedStatement ms = sqlMapClient.getMappedStatement(sqlId);
        if (ms == null) {
            //TODO:建议封转自己的异常对象
            throw new RuntimeException("can't find MappedStatement.");
        }

        //按照Ibatis代码,得到Sql和Param信息
        RequestScope request = new RequestScope();
        ms.initRequest(request);
        Sql sql = ms.getSql();
        String sqlValue = sql.getSql(request, param);
        Object[] sqlParam = sql.getParameterMap(request, param).getParameterObjectValues(request, param);

        //组转返回对象
        SqlStatement ret = new SqlStatement();
        ret.setSql(sqlValue);
        ret.setParam(sqlParam);
        return ret;
    }

    /**
     * 设置SqlMapClient对象
     */
    public void setSqlMapClient(ExtendedSqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }

    /**
     * <pre>
     * 不推荐使用
     * 推荐使用: {@link IbatisSqlTester#setSqlMapClient(ExtendedSqlMapClient)}
     * TODO:请去除这个方法,或者增加初始化的方式
     * </pre>
     * 
     * @param sqlMapConfig sqlMapConfig xml文件
     */
    public void setSqlMapConfig(String sqlMapConfig) {
        InputStream in = null;
        try {
            File file = ResourceUtils.getFile(sqlMapConfig);
            in = new FileInputStream(file);
            this.sqlMapClient = (ExtendedSqlMapClient) SqlMapClientBuilder.buildSqlMapClient(in);
        } catch (Exception e) {
            throw new RuntimeException("sqlMapConfig init error.", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
    }

}
