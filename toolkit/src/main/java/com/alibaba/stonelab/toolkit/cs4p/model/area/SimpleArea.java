/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.cs4p.model.area;

import org.springframework.util.Assert;

import com.alibaba.stonelab.toolkit.cs4p.model.Area;
import com.alibaba.stonelab.toolkit.cs4p.model.Point;

/**
 * @author Stone.J 2010-9-17 下午06:40:00
 */
public class SimpleArea implements Area {

	private Point point;
	private long radius;

	public SimpleArea(Point point, long radius) {
		this.point = point;
		this.radius = radius;
	}

	public boolean isCross(Area area) {
		Assert.isInstanceOf(SimpleArea.class, area);
		Assert.notNull(area);

		SimpleArea sa = (SimpleArea) area;
		Assert.notNull(sa.getPoint());
		double x = (double) sa.getPoint().getX() - point.getX();
		double y = (double) sa.getPoint().getY() - point.getY();
		double r = Math.pow(Math.pow(x, 2) + Math.pow(y, 2), 0.5);
		return r < (radius + sa.getRadius());
	}

	public int getCrossPercentage(Area area) {
		Assert.isInstanceOf(SimpleArea.class, area);
		Assert.notNull(area);

		SimpleArea sa = (SimpleArea) area;
		Assert.notNull(sa.getPoint());

		long total = this.getRadius() + sa.getRadius();
		double x = (double) sa.getPoint().getX() - point.getX();
		double y = (double) sa.getPoint().getY() - point.getY();
		double r = Math.pow(Math.pow(x, 2) + Math.pow(y, 2), 0.5);

		int ret = 100 - (int) ((r / total) * 100);
		return ret < 0 ? 0 : ret;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public long getRadius() {
		return radius;
	}

	public void setRadius(long radius) {
		this.radius = radius;
	}

}
