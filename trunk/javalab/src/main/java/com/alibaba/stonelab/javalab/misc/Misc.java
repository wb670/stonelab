/*
 * Copyright 1999-2011 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.misc;

import java.util.concurrent.CyclicBarrier;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-1-26
 */
public class Misc {

	private int i;

	public class Worker implements Runnable {

		private CyclicBarrier barrier;

		public Worker(CyclicBarrier barrier) {
			this.barrier = barrier;
			System.out.println(i);
		}

		@Override
		public void run() {
			try {
				barrier.await();
			} catch (Exception e) {
			}
			System.out.println(Thread.currentThread().getName() + ":" + System.currentTimeMillis());
		}

	}
}
