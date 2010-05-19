/**
 * Function: 
 * 
 * File Created at 2010-5-12
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.btrace.trace;

import static com.sun.btrace.BTraceUtils.println;
import static com.sun.btrace.BTraceUtils.str;
import static com.sun.btrace.BTraceUtils.strcat;

import java.sql.Connection;

import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback;
import com.ibatis.sqlmap.engine.scope.RequestScope;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.Self;

/**
 * @author li.jinl
 */
@BTrace
public class SqlTracer {

    private static final String CLASS  = "com.ibatis.sqlmap.engine.execution.SqlExecutor";
    private static final String METHOD = "executeQuery";

    @OnMethod(clazz = CLASS, method = METHOD)
    public static void onExecuteQuery(@Self SqlExecutor self, RequestScope request, Connection conn, String sql,
                                      Object[] parameters, int skipResults, int maxResults, RowHandlerCallback callback) {
        println(strcat(strcat(sql, ":"), str(parameters)));
    }
}
