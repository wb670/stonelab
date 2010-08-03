package com.alibaba.stonelab.webxsample.sample.web.module.action;

import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.alibaba.stonelab.webxsample.sample.service.domain.Sample;

public class HelloAction {

    public void doGreeting(@FormGroup("sample") Sample sample, Navigator nav) {
        String name = sample.getName();
        nav.redirectTo("sampleLink").withTarget("hello").withParameter("name", name);
    }

}
