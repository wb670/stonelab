/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.stonelab.webxsample.sample.service.impl;

import com.alibaba.stonelab.webxsample.sample.service.domain.Sample;

/**
 * @author Stone.J 2010-8-19 ÏÂÎç05:00:33
 */
public class SampleService {

    public Sample getSample() {
        Sample sample = new Sample();
        sample.setName("Stone.J");
        return sample;
    }

}
