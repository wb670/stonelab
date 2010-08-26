/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.stonelab.webxsample.sample.helloworld;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import com.alibaba.citrus.springext.support.parser.AbstractNamedBeanDefinitionParser;
import com.alibaba.citrus.springext.util.SpringExtUtil;

/**
 * @author Stone.J 2010-8-26 ÏÂÎç04:39:50
 */
public class CustomServiceBeanDefinitionParser extends AbstractNamedBeanDefinitionParser<CustomService> {

    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        SpringExtUtil.attributesToProperties(element, builder, "msg");
    }

    @Override
    protected String getDefaultName() {
        return "customService";
    }

}
