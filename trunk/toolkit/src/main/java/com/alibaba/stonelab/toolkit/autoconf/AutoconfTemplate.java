/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.autoconf;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.IOUtils;

/**
 * @author li.jinl 2010-7-13 上午11:31:14
 */
public class AutoconfTemplate {

    private String      file;
    private Set<String> props = new HashSet<String>();

    public AutoconfTemplate(String file){
        this.file = file;
        init();
    }

    public boolean contains(String prop) {
        return props.contains(prop);
    }

    public String getFile() {
        return file;
    }

    public Set<String> getProps() {
        return props;
    }

    private void init() {
        try {
            parser();
        } catch (Exception e) {
            System.out.println(e);
            throw new AutoconfException("Parse Template Error.");
        }
    }

    private void parser() throws Exception {
        InputStream in = new FileInputStream(file);
        String content = IOUtils.toString(in);
        IOUtils.closeQuietly(in);
        props.addAll(AutoconfUtil.parsePlaceholder(content));
    }
}
