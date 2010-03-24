/**
 * Function: 
 * 
 * File Created at 2010-3-11
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.misc;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author li.jinl
 */
public class WikiHelper {

    private static final String FILE_NAME     = "d:/tmp/antx.txt";
    private static final String WIKI_TEMPLATE = "|| 搜索推荐项目 | {0} | {1} | {2} |";

    public static void main(String[] args) throws Exception {
        InputStream input = new FileInputStream(FILE_NAME);
        List<String> lines = IOUtils.readLines(input);
        for (String line : lines) {
            String[] keyValue = line.split("=");
            String key = keyValue[0];
            String value = keyValue[1];
            String project = StringUtils.substringBefore(key, ".");
            String ret = MessageFormat.format(WIKI_TEMPLATE, project, key, value);
            System.out.println(ret);
        }
        IOUtils.closeQuietly(input);
    }
}
