package com.alibaba.stonelab.webxsample.sample.web.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.common.logging.Logger;
import com.alibaba.common.logging.LoggerFactory;

public class Hello {

    private static final Logger LOG = LoggerFactory.getLogger(Hello.class);

    public void execute(@Param("name") String name, Context context) {
        LOG.info("Hello Screen");
        context.put("name", name);
    }

}
