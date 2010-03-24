/**
 * Function: 
 * 
 * File Created at 2010-2-2
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.log.biz.p2;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.javalab.log.biz.BizRandom;

/**
 * TODO Comment of Service
 * 
 * @author li.jinl
 */
public class Service {

    private static final Log       LOG        = LogFactory.getLog("p2");
    private static final LogFormat LOG_FORMAT = new LogFormat();

    private Dao                    dao        = new Dao();

    public void logic(String memberId) {
        //参数不对，记录业务日志
        if (!isParameterValid()) {
            LOG.info(LOG_FORMAT.getLog(memberId, "False", "False", "Null", "Null"));
            return;
        }
        //是否在白名单，在则记录成功--特殊的成功
        if (isInWhitelist()) {
            LOG.info(LOG_FORMAT.getLog(memberId, "True", "True", "True", "Null"));
            return;
        }

        List<String> list = dao.getData(memberId);
        if (list == null) {
            LOG.info(LOG_FORMAT.getLog(memberId, "False", "True", "False", "Fasle"));
        } else {
            LOG.info(LOG_FORMAT.getLog(memberId, "True", "True", "False", "True"));
        }

        return;
    }

    private boolean isParameterValid() {
        return BizRandom.isSuccessful();
    }

    private boolean isInWhitelist() {
        return BizRandom.isSuccessful();
    }

    private static final class LogFormat {

        private static final String LOG_PATTERN = "Service_Execute: [Parameter:{0}] [Result:{1}] [ParameterValid:{2}] [Whitelist:{3}] [Dao:{4}]";

        public String getLog(String parameter, String result, String parameterValid,
                             String whitelist, String dao) {
            return MessageFormat.format(LOG_PATTERN, parameter, result, parameterValid, whitelist,
                    dao);
        }
    }
}
