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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.time.DateUtils;

/**
 * @author li.jinl
 */
public class ValidatedPeriod {

    public static final String    DEBUG_TXT       = "2010-02-23 14:42:14,052  ERROR - [Error Type]:SIZE_ERROR,memberId:binhai,ccbuList:[enableDate:2009-03-18;disableDate:2010-03-18;tpPackage:ETP_LIMITED_EDITION, enableDate:2008-03-18;disableDate:2009-03-18;tpPackage:ETP_LIMITED_EDITION, enableDate:2007-03-18;disableDate:2008-03-18;tpPackage:ETP_LIMITED_EDITION, enableDate:2006-03-18;disableDate:2007-03-18;tpPackage:ETP_LIMITED_EDITION, enableDate:2005-03-18;disableDate:2006-03-18;tpPackage:ETP_LIMITED_EDITION, enableDate:2004-03-18;disableDate:2005-03-18;tpPackage:ETP_LIMITED_EDITION, enableDate:2003-03-07;disableDate:2004-03-07;tpPackage:ETP_LIMITED_EDITION, enableDate:2002-03-07;disableDate:2003-03-07;tpPackage:ETP_LIMITED_EDITION],pccList:[enableDate:2009-03-18;disableDate:2010-03-18;tpPackage:ETP_LIMITED_EDITION, enableDate:2008-03-18;disableDate:2009-03-18;tpPackage:ETP_LIMITED_EDITION, enableDate:2007-03-18;disableDate:2008-03-18;tpPackage:ETP_LIMITED_EDITION, enableDate:2006-03-18;disableDate:2007-03-18;tpPackage:ETP_LIMITED_EDITION, enableDate:2005-03-18;disableDate:2006-03-18;tpPackage:ETP_LIMITED_EDITION],info:null";

    private static final String   PATTERN_STR     = ".*memberId:(.*),ccbuList:\\[(.*)\\],pccList:\\[(.*)\\].*";
    private static final Pattern  PATTERN         = Pattern.compile(PATTERN_STR);

    private static final String[] DATE_PATTERNS   = { "yyyy-MM-dd" };

    private static final String   TAG_ENABLEDATE  = "enableDate:";
    private static final String   TAG_DISABLEDATE = "disableDate:";
    private static final String   TAG_TPPACKAGE   = "tpPackage:";

    private String                memberId;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             //会员id
    private List<Period>          sitePeriods;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          //中文站服务期
    private List<Period>          pccPeriods;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           //pcc服务期

    public static void main(String[] args) {
        ValidatedPeriod vp = ValidatedPeriod.parse(DEBUG_TXT);
        System.out.println(vp.getPccPeriods());
    }

    /**
     * 解析
     * 
     * @param text 文本信息
     * @return 被验证的服务期信息
     */
    public static ValidatedPeriod parse(String text) {
        ValidatedPeriod vp = null;
        try {
            Matcher matcher = PATTERN.matcher(text);
            if (!matcher.matches() || matcher.groupCount() != 3) {
                //ignore instead of throwing exception.
                //throw new RuntimeException();
                return null;
            }

            String memberIdStr = matcher.group(1);
            String ccbuStr = matcher.group(2);
            String pccStr = matcher.group(3);

            vp = new ValidatedPeriod();
            //设置memberId
            vp.memberId = memberIdStr;
            //设置网站服务期
            vp.sitePeriods = parsePeriods(ccbuStr);
            //设置pcc服务期
            vp.pccPeriods = parsePeriods(pccStr);

            Collections.sort(vp.sitePeriods);
            Collections.sort(vp.pccPeriods);
        } catch (Exception e) {
            throw new RuntimeException("parse error.");
        }

        return vp;
    }

    private static List<Period> parsePeriods(String str) throws Exception {
        List<Period> list = new ArrayList<Period>();
        String[] value = StringUtils.split(str, ",");
        for (int i = 0; i < value.length; i++) {
            String[] periodStr = StringUtils.split(value[i], ";");
            Date start = parseDate(StringUtils.substringAfter(periodStr[0], TAG_ENABLEDATE));
            Date end = parseDate(StringUtils.substringAfter(periodStr[1], TAG_DISABLEDATE));
            String type = StringUtils.substringAfter(periodStr[2], TAG_TPPACKAGE);
            list.add(new Period(start, end, type));
        }
        return list;
    }

    private static Date parseDate(String str) throws Exception {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        return DateUtils.parseDate(str, DATE_PATTERNS);
    }

    public String getMemberId() {
        return memberId;
    }

    public List<Period> getSitePeriods() {
        return sitePeriods;
    }

    public List<Period> getPccPeriods() {
        return pccPeriods;
    }

    /**
     * 服务期信息
     * 
     * @author li.jinl
     */
    public static class Period implements Comparable<Period> {

        private Date   start; // 开始时间
        private Date   end;  //结束时间
        private String type; //服务类型

        public Period(Date start, Date end, String type) {
            this.start = start;
            this.end = end;
            this.type = type;
        }

        public Date getStart() {
            return start;
        }

        public void setStart(Date start) {
            this.start = start;
        }

        public Date getEnd() {
            return end;
        }

        public void setEnd(Date end) {
            this.end = end;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return new ReflectionToStringBuilder(this, DateFormatToStringStyle.DATE_FORMAT_TO_STRING_STYLE).toString();
        }

        @Override
        public int compareTo(Period p) {
            if (start == null || p.getStart() == null) {
                return 1;
            }
            if (start.getTime() == p.getStart().getTime()) {
                return 0;
            } else if (start.getTime() > p.getStart().getTime()) {
                return 1;
            } else {
                return -1;
            }
        }
    }

}
