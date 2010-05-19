/**
 * Function: 
 * 
 * File Created at 2010-4-29
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.tool.fetion.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author li.jinl
 */
public class Fetion {

    public void sendMsg(String mobile, String msg) throws RuntimeException {
        if (!StringUtils.isNumeric(mobile) || mobile.length() != 11) {
            throw new RuntimeException("Mobile ERROR.");
        }
        if (StringUtils.isEmpty(msg) || msg.length() > 60) {
            throw new RuntimeException("Message ERROR.");
        }
        List<String> command = new ArrayList<String>();
        command.add(FetionConfig.FETION_EXE);
        command.add(FetionConfig.PARAM_MOBILE + FetionConfig.MOBILE);
        command.add(FetionConfig.PARAM_PASSWORD + FetionConfig.PASSWORD);
        command.add(FetionConfig.PARAM_TO + mobile);
        command.add(FetionConfig.PARAM_MSG + msg);

        ProcessBuilder builder = new ProcessBuilder(command);
        Process p = null;
        try {
            p = builder.start();
            IOUtils.toString(p.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Send ERROR.");
        } finally {
            if (p != null) {
                p.destroy();
            }
        }
    }
}
