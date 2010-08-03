/**
 * Function: 
 * 
 * File Created at 2010-3-14
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.stonelab.toolkit.learning.instrumentation.agent;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author li.jinl
 */
public class ModelTransformer implements ClassFileTransformer {

    private static final String MODEL_CLASS = "com/alibaba/javalab/instrumentation/Model";

    private String              modelResource;

    public ModelTransformer(String modelResource) {
        this.modelResource = modelResource;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer)
            throws IllegalClassFormatException {
        if (!className.equals(MODEL_CLASS)) {
            return null;
        }
        return getNewModel();
    }

    protected byte[] getNewModel() {
        try {
            InputStream in = new FileInputStream(modelResource);
            byte[] bytes = new byte[1024];
            int length = in.read(bytes);
            byte[] ret = new byte[length];
            System.arraycopy(bytes, 0, ret, 0, length);
            return ret;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

}
