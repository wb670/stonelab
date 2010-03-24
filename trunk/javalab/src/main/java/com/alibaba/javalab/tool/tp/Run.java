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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author li.jinl
 */
public class Run {

    private static final Log             LOG                  = LogFactory.getLog("p1");

    private static final String          LOG_FILE             = "d:/tmp/verify_period_error.log";
    //private static final String          LOG_FILE             = "d:/tmp/tmp.log";

    private static final PeriodValidator validator            = new PeriodValidator();

    private static final String          TYPE_NULL_DATE_CCBU  = "TYPE_NULL_DATE_CCBU";
    private static final String          TYPE_NULL_DATE_PCC   = "TYPE_NULL_DATE_PCC";
    private static final String          TYPE_START_END_CCBU  = "TYPE_START_END_CCBU";
    private static final String          TYPE_START_END_PCC   = "TYPE_START_END_PCC";
    private static final String          TYPE_CROSS_DATE_CCBU = "TYPE_CROSS_DATE_CCBU";
    private static final String          TYPE_CROSS_DATE_PCC  = "TYPE_CROSS_DATE_PCC";

    public static void main(String[] args) {
        Run.validate();
    }

    public static void validate() {
        try {
            InputStream input = new FileInputStream(LOG_FILE);
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String line = null;
            while ((line = br.readLine()) != null) {
                try {
                    ValidatedPeriod vp = ValidatedPeriod.parse(line);
                    if (vp == null) {
                        continue;
                    }
                    try {
                        //pcc periods
                        print(TYPE_NULL_DATE_PCC, validator.nullDateValidate(vp.getPccPeriods()));
                        print(TYPE_START_END_PCC, validator.startEndValidate(vp.getPccPeriods()));
                        print(TYPE_CROSS_DATE_PCC, validator.crossValidate(vp.getPccPeriods()));

                        //ccbut
                        print(TYPE_NULL_DATE_CCBU, validator.nullDateValidate(vp.getSitePeriods()));
                        print(TYPE_START_END_CCBU, validator.startEndValidate(vp.getSitePeriods()));
                        print(TYPE_CROSS_DATE_CCBU, validator.crossValidate(vp.getSitePeriods()));
                    } catch (PeriodException e) {
                        continue;
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            IOUtils.closeQuietly(input);
        } catch (FileNotFoundException e) {
            System.out.println("file not found.");
        } catch (IOException e) {
            System.out.println("io error.");
        }
    }

    private static void print(String type, List<?> list) throws PeriodException {
        if (!list.isEmpty()) {
            LOG.info(type + ":" + list);
            throw new PeriodException();
        }
    }

    public static class PeriodException extends Exception {

        private static final long serialVersionUID = -4856579026269042284L;

    }

}
