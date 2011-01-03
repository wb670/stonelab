/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.learning.examples;

import org.easymock.EasyMock;
import org.easymock.IAnswer;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2010-12-22
 */
public class EsayMockExample {

    public static void main(String[] args) throws Exception {
        Bean mock = EasyMock.createMock(Bean.class);
        EasyMock.expect(mock.hello()).andReturn("nhello").anyTimes();
        EasyMock.expect(mock.test()).andAnswer(new IAnswer<String>() {

            @Override
            public String answer() throws Throwable {
                return "ntest";
            }
        });
        EasyMock.replay(mock);
        System.out.println(mock.hello());
        System.out.println(mock.hello());
        System.out.println(mock.hello());
        System.out.println(mock.test());
    }

    public static class Bean {

        public String hello() {
            return "hello ";
        }

        public String test() {
            return "test";
        }
    }

}
