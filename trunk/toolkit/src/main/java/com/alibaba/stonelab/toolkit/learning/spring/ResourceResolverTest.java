/**
 * Function: 
 * 
 * File Created at 2010-7-22
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.stonelab.toolkit.learning.spring;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

/**
 * @author li.jinl
 */
public class ResourceResolverTest {

    /* 资源路径 */
    private static final String                  PATH           = "classpath*:com/alibaba/javalab/t*/**/*.class";
    /* 资源解析器 */
    private static final ResourcePatternResolver RESOLVER       = new PathMatchingResourcePatternResolver();
    /* Meta信息Reader Factory.用于创建MetaReader */
    private static final MetadataReaderFactory   READER_FACTORY = new SimpleMetadataReaderFactory();

    public static void main(String[] args) throws Exception {
        //根据正则表达式,得到资源列表
        Resource[] resources = RESOLVER.getResources(PATH);
        for (Resource res : resources) {
            //通过 MetadataReader得到ClassMeta信息,打印类名
            MetadataReader meta = READER_FACTORY.getMetadataReader(res);
            System.out.println(meta.getClassMetadata().getClassName());
        }
    }
}
