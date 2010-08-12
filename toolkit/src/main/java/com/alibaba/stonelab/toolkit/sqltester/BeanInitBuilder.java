/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.sqltester;

import java.beans.PropertyDescriptor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * <pre>
 * Bean初始化Build工具.
 * 将基本类型,默认初始化:
 * byte short int     long float double => 0
 * Byte Short Integer Long Float Double => 0
 * String => $propertyName
 * Date => 2000-01-01 01:01:01
 * JDK Enum => Enum的第一个元素
 * 
 * TODO:
 * 1.可以考虑加上回调接口,允许修改特殊的属性值
 * 2.JDK Enum可以轮询设值
 * </pre>
 * 
 * @author Stone.J 2010-8-9 下午05:35:05
 */
public class BeanInitBuilder<T> {

    private static final String               DEFAULT_DATE_PATTERN   = "yyyy-MM-dd HH:mm:ss";
    private static final String               DEFAULT_VALUE_DATE_STR = "2000-01-01 01:01:01";

    private static Map<Class, ? super Number> DEFAULT_VALUE_NUMBER   = new HashMap<Class, Number>();
    private static Date                       DEFAULT_VALUE_DATE;

    static {
        //初始化数值
        DEFAULT_VALUE_NUMBER.put(Byte.class, (byte) 0);
        DEFAULT_VALUE_NUMBER.put(Short.class, (short) 0);
        DEFAULT_VALUE_NUMBER.put(Integer.class, (int) 0);
        DEFAULT_VALUE_NUMBER.put(Long.class, (long) 0);
        DEFAULT_VALUE_NUMBER.put(Float.class, (float) 0.0);
        DEFAULT_VALUE_NUMBER.put(Double.class, (double) 0.0);
        //初始化日期
        try {
            DEFAULT_VALUE_DATE = new SimpleDateFormat(DEFAULT_DATE_PATTERN).parse(DEFAULT_VALUE_DATE_STR);
        } catch (ParseException e) {
            throw new BuildException("date init error.", e);
        }
    }

    //初始化Bean的class
    private Class<T>                          clz;

    public BeanInitBuilder(Class<T> clz) {
        this.clz = clz;
    }

    /**
     * 单个bean build
     * 
     * @return bean
     */
    public T build() {
        return doBuild(1).get(0);
    }

    /**
     * 多个bean build
     * 
     * @param count 需要初始化bean的个数
     * @return beans
     */
    public List<T> build(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("count must be greater than zero");
        }
        return doBuild(count);
    }

    /**
     * <pre>
     * build logic 
     * TODO:
     * 1.可以考虑加上回调,修改特殊的属性值
     * 2.JDK Enum可以考虑轮询设值
     * 
     * </pre>
     * 
     * @param count
     * @return
     */
    private List<T> doBuild(int count) {
        List<T> ret = new ArrayList<T>();
        for (int i = 0; i < count; i++) {
            T t = null;
            try {
                t = clz.newInstance();
            } catch (Exception e) {
                throw new BuildException("new instance error.", e);
            }
            PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(clz);
            for (PropertyDescriptor des : descriptors) {
                try {
                    //数值的处理
                    if (Number.class.isAssignableFrom(des.getPropertyType())) {
                        PropertyUtils.setProperty(t, des.getName(), DEFAULT_VALUE_NUMBER.get(des.getPropertyType()));
                    }
                    //String处理
                    if (String.class.isAssignableFrom(des.getPropertyType())) {
                        if (count == 1) {
                            PropertyUtils.setProperty(t, des.getName(), des.getName());
                        } else {
                            PropertyUtils.setProperty(t, des.getName(), des.getName() + i);
                        }
                    }
                    //date处理
                    if (Date.class.isAssignableFrom(des.getPropertyType())) {
                        PropertyUtils.setProperty(t, des.getName(), DEFAULT_VALUE_DATE);
                    }
                    //JDK枚举处理
                    if (Enum.class.isAssignableFrom(des.getPropertyType())) {
                        if (des.getPropertyType().getEnumConstants().length >= 1) {
                            PropertyUtils.setProperty(t, des.getName(), des.getPropertyType().getEnumConstants()[0]);
                        }
                    }
                } catch (Exception e) {
                    throw new BuildException("build error.", e);
                }
            }
            //赋值数组
            ret.add(t);
        }
        return ret;
    }

    /**
     * BeanInitBuilder build 过程 异常
     * 
     * @author Stone.J 2010-8-10 下午04:39:12
     */
    public static class BuildException extends RuntimeException {

        private static final long serialVersionUID = -126401263254987600L;

        public BuildException(String msg, Throwable e) {
            super(msg, e);
        }
    }

}
