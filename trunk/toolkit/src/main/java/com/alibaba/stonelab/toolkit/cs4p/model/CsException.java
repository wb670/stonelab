/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.cs4p.model;

/**
 * @author Stone.J 2010-9-17 下午03:01:01
 */
public class CsException extends RuntimeException {

	private static final long serialVersionUID = 1706494635821850814L;

	private Code code;

	public CsException(Code code) {
		this.code = code;
	}

	public Code getCode() {
		return code;
	}

	public static enum Code {
		/** 移动步伐过大 */
		STEP_OVERFLOW("你以为会漂啊?"),
		/** 未激活 */
		INACTIVE("你还没有上战场呢."),
		/** 阵亡 */
		DEAD("你已经玩完了."),
		/** MULTI_PLAYERS */
		MULTI_PLAYERS("你创建多人了."),
		/***/
		NOT_START("对战未开始");

		private Code(String des) {
			this.des = des;
		}

		private String des;

		public String getDes() {
			return des;
		}

	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}

}
