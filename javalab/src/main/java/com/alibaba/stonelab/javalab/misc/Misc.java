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
        Hello<?> hello = new HelloImpl();
        hello.hello().hello();
    }

    public static interface Hello<H extends Hello<?>> {

        H hello();

    }

    public static class HelloImpl implements Hello<Hello<?>> {

        @Override
        public Hello<?> hello() {
            System.out.println("hello");
            return this;
        }

    }

}
