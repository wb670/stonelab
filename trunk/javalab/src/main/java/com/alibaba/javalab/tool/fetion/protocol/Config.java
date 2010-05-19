/**
 * Function: 
 * 
 * File Created at 2010-4-28
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.tool.fetion.protocol;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * @author li.jinl
 */
public class Config {

    private static final String         RESOURCE = "com.alibaba.javalab.tool.fetion.config";
    private static final ResourceBundle BUNDLE   = ResourceBundle.getBundle(RESOURCE);

    public static String getProperties(String key) {
        return BUNDLE.getString(key);
    }

    public static String getProperties(String key, Object... data) {
        return MessageFormat.format(BUNDLE.getString(key), data);
    }

}
