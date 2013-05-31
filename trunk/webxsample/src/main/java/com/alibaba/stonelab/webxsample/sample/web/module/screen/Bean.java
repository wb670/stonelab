/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.stonelab.webxsample.sample.web.module.screen;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.alibaba.citrus.turbine.Context;

/**
 * @author Stone.J 2010-8-31 ÏÂÎç04:59:45
 */
public class Bean implements ApplicationContextAware {

	private ApplicationContext ctx;

	@SuppressWarnings("unchecked")
	public void execute(Context context) {
		Map<String, Object> root = (Map<String, Object>) ctx.getParent().getBeansOfType(Object.class);
		Map<String, Object> component = ctx.getBeansOfType(Object.class);

		Collection<Object> rbs = root.values();
		Collection<Object> cbs = component.values();

		System.out.println("Root Beans:");
		System.out.println(rbs);
		System.out.println();
		System.out.println("Component Beans:");
		System.out.println(cbs);

		context.put("root", rbs);
		context.put("component", cbs);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ctx = applicationContext;
	}

}
