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

import static com.sun.btrace.BTraceUtils.addToAggregation;
import static com.sun.btrace.BTraceUtils.currentThread;
import static com.sun.btrace.BTraceUtils.name;
import static com.sun.btrace.BTraceUtils.newAggregation;
import static com.sun.btrace.BTraceUtils.printAggregation;
import static com.sun.btrace.BTraceUtils.println;
import static com.sun.btrace.BTraceUtils.timeMillis;

import com.sun.btrace.aggregation.Aggregation;
import com.sun.btrace.aggregation.AggregationFunction;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.OnTimer;
import com.sun.btrace.annotations.TLS;

/**
 * @author li.jinl
 */
@BTrace
public class TimeTracer {

    private static final String      CLASS   = "com.alibaba.exodus2.web.member.module.screen.Myalibaba";
    private static final String      METHOD  = "execute";

    private static final Aggregation average = newAggregation(AggregationFunction.AVERAGE);
    private static final Aggregation max     = newAggregation(AggregationFunction.MAXIMUM);
    private static final Aggregation min     = newAggregation(AggregationFunction.MINIMUM);
    private static final Aggregation sum     = newAggregation(AggregationFunction.SUM);
    private static final Aggregation count   = newAggregation(AggregationFunction.COUNT);

    //开始时间
    @TLS
    private static long              start;

    //线程名
    @TLS
    private static String            name;

    /**
     * 记录开始时间
     */
    @OnMethod(clazz = CLASS, method = METHOD, location = @Location(Kind.ENTRY))
    public static void start() {
        start = timeMillis();
        name = name(currentThread());
    }

    /**
     * 记录结束时间
     */
    @OnMethod(clazz = CLASS, method = METHOD, location = @Location(Kind.RETURN))
    public static void end() {
        if (name != null) {
            long duration = (long) ((timeMillis() - start));
            addToAggregation(average, duration);
            addToAggregation(count, duration);
            addToAggregation(max, duration);
            addToAggregation(min, duration);
            addToAggregation(sum, duration);
            addToAggregation(count, duration);
        }
    }

    @OnTimer(2000)
    public static void report() {
        println("--------------------------------------------------------------------------");
        printAggregation("Total: ", count);
        printAggregation("Average: ", average);
        printAggregation("Min: ", min);
        printAggregation("Max: ", max);
        printAggregation("Sum: ", sum);
        println("--------------------------------------------------------------------------");
    }

}
