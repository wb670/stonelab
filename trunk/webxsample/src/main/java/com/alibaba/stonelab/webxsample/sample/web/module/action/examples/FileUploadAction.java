/*
 * Copyright 1999-2011 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.webxsample.sample.web.module.action.examples;

import org.apache.commons.fileupload.FileItem;

import com.alibaba.citrus.turbine.dataresolver.Param;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-2-17
 */
public class FileUploadAction {

    public void doUpload(@Param(name = "file") FileItem file) {
        System.out.println(file);
    }

}
