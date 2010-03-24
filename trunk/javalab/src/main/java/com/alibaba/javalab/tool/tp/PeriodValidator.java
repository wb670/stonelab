/**
 * Function: 
 * 
 * File Created at 2010-2-23
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.tool.tp;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.javalab.tool.tp.ValidatedPeriod.Period;

/**
 * 判断服务期数据
 * 
 * @author li.jinl
 */
public class PeriodValidator {

    /**
     * 判断start,end日期是否为空
     * 
     * @param periods
     * @return
     */
    public List<Period> nullDateValidate(List<Period> periods) {
        List<Period> list = new ArrayList<Period>(periods.size());
        for (Period period : periods) {
            if (isNullDate(period)) {
                list.add(period);
            }
        }
        return list;
    }

    /**
     * 判断start,end日期是否合理
     * 
     * @param periods
     * @return
     */
    public List<Period> startEndValidate(List<Period> periods) {
        List<Period> list = new ArrayList<Period>(periods.size());
        for (Period period : periods) {
            if (isStartGtEnd(period)) {
                list.add(period);
            }
        }
        return list;
    }

    /**
     * 判断日期是否存在交集
     * 
     * @param periods
     * @return
     */
    public List<CrossPeriod> crossValidate(List<Period> periods) {
        List<CrossPeriod> list = new ArrayList<CrossPeriod>(periods.size());
        for (int i = 0; i < periods.size(); i++) {
            Period period = periods.get(i);

            for (int j = i + 1; j < periods.size(); j++) {
                Period otherPeriod = periods.get(j);
                if (isCross(period, otherPeriod)) {
                    list.add(new CrossPeriod(period, otherPeriod));
                }
            }

        }
        return list;
    }

    private boolean isNullDate(Period period) {
        return (period.getStart() == null || period.getEnd() == null);
    }

    private boolean isStartGtEnd(Period period) {
        return period.getStart().getTime() > period.getEnd().getTime();
    }

    private boolean isCross(Period period, Period otherPeriod) {
        return period.getEnd().getTime() > otherPeriod.getStart().getTime();
    }

    /**
     * 交叉的服务期记录
     * 
     * @author li.jinl
     */
    public static class CrossPeriod {

        private Period period;
        private Period crossPeriod;

        public CrossPeriod(Period period, Period crossPeriod) {
            this.period = period;
            this.crossPeriod = crossPeriod;
        }

        public Period getPeriod() {
            return period;
        }

        public void setPeriod(Period period) {
            this.period = period;
        }

        public Period getCrossPeriod() {
            return crossPeriod;
        }

        public void setCrossPeriod(Period crossPeriod) {
            this.crossPeriod = crossPeriod;
        }

        @Override
        public String toString() {
            return "CrossPeriod [period=" + period + ", crossPeriod=" + crossPeriod + "]";
        }

    }

}
