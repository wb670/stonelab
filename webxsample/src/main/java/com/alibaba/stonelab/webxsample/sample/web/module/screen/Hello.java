package com.alibaba.stonelab.webxsample.sample.web.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;

public class Hello {

    public void execute(@Param("name") String name, Context context) {
        context.put("name", name);
    }

}
