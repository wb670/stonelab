/**
 * Function: 
 * 
 * File Created at 2010-2-24
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.tool.tp;

import java.util.Date;

import org.apache.commons.lang.builder.StandardToStringStyle;
import org.apache.commons.lang.time.DateFormatUtils;

/**
 * @author li.jinl
 */
public class DateFormatToStringStyle extends StandardToStringStyle {

    private static final long                   serialVersionUID            = 385048502081481784L;

    private static final String                 DEFAULT_DATE_FORMAT         = "yyyy-MM-dd";

    public static final DateFormatToStringStyle DATE_FORMAT_TO_STRING_STYLE = new DateFormatToStringStyle();

    @Override
    protected void appendDetail(StringBuffer buffer, String fieldName, Object value) {
        if (value instanceof Date) {
            String val = DateFormatUtils.format((Date) value, DEFAULT_DATE_FORMAT);
            super.appendDetail(buffer, fieldName, val);
            return;
        }
        super.appendDetail(buffer, fieldName, value);
    }

}
