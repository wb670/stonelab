/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.cs4p.model;

/**
 * @author Stone.J 2010-9-19 下午04:04:49
 */
public class Info {

	private String id;
	private String name;
	private boolean active;
	private int blood;
	private boolean live;

	public Info(String id, String name) {
		this.id = id;
		this.name = name;

		this.active = false;
		this.blood = 100;
		this.live = true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public int getBlood() {
		return blood;
	}

	public void setBlood(int blood) {
		this.blood = blood;
	}

	public void reduceBlood(int value) {
		if (value <= 0) {
			return;
		}
		if (this.blood - value <= 0) {
			this.blood = 0;
		} else {
			this.blood = this.blood - value;
		}
	}
}
