/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.sqltester;

/**
 * <pre>
 * SqlStatement:Sql语句对象.
 * 包含:
 *  1.sql语句,类似  select * from offer where id = ? and member_id = ?
 *  2.参数值,类似 [1,stone2083]
 *  
 *  toString方法,返回执行的sql语句,如:
 *  select * from offer where id = '1' and member_id = 'stone2083'
 * </pre>
 * 
 * @author Stone.J 2010-8-9 下午02:55:36
 */
public class SqlStatement {

    //sql
    private String   sql;
    //sql参数
    private Object[] param;

    /**
     * <pre>
     * 输出最终执行的sql内容.
     * 将sql和param进行merge,产生最终执行的sql语句
     * </pre>
     */
    @Override
    public String toString() {
        return merge();
    }

    /**
     * <pre>
     * 将sql进行格式化.
     * 
     * 目前只是简单进行格式化.去除前后空格,已经重复空格
     * TODO:请使用统一格式化标准规,建议使用SqlFormater类,进行处理
     * </pre>
     * 
     * @param sql
     * @return
     */
    protected String format(String sql) {
        if (sql == null) {
            return null;
        }
        return sql.toLowerCase().trim().replaceAll("\\s{1,}", " ");
    }

    /**
     * <pre>
     * 将sql和param进行merge.
     * TODO:请严格按照SQL标准,进行merge sql内容
     * </pre>
     */
    protected String merge() {
        if (param == null || param.length == 0) {
            return this.sql;
        }
        String ret = sql;
        for (Object p : param) {
            ret = ret.replaceFirst("\\?", "'" + p.toString() + "'");
        }
        return ret;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = format(sql);
    }

    public Object[] getParam() {
        return param;
    }

    public void setParam(Object[] param) {
        this.param = param;
    }
}
