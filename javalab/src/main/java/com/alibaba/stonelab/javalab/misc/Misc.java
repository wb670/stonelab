/*
 * Copyright 1999-2011 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.misc;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-1-26
 */
public class Misc {

	public static void main(String[] args) {
		int i = 10;
		if (i > 9) {
			System.out.println(9);
		} else if (i > 8) {
			System.out.println(8);
		} else if (i > 7) {
			System.out.println(7);
		} else {
			System.out.println(6);
		}
	}

	public static class Child extends Parent {
		public Child(String name) {
			System.out.println(name);
		}
	}

	public static class Parent {
		public Parent() {
			System.out.println("parent.");
		}

		public Parent(String name) {
			System.out.println("parent. " + name);
		}
	}

}
