/**
 * Function: 
 * 
 * File Created at 2010-4-30
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.tool.fetion.app.plugins;

import com.alibaba.javalab.tool.fetion.app.Fetion;

/**
 * @author li.jinl
 */
public class Report {

    private static final Weather weather = new Weather();
    private static final Fetion  fetion  = new Fetion();

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("param error.");
            System.exit(0);
        }
        String mobile = args[0];
        try {
            fetion.sendMsg(mobile, weather.getWeatherInfo());
        } catch (RuntimeException e) {
            System.out.println("ERROR.");
        }
        System.out.println("OK.");
    }

}
