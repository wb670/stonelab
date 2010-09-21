/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.learning.perf;

import net.sf.cglib.beans.BulkBean;

/**
 * @author Stone.J 2010-9-16 下午12:29:09
 */
public class BulkBeanTest extends ReflectionTest {

    private static final CachedBulkBean CACHED_BULK_BEAN = new CachedBulkBean();
    private static final Bean           BEAN             = new Bean();

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (long i = 0; i < LOOP; i++) {
            testBulkBean();
        }
        long dur = System.currentTimeMillis() - start;
        System.out.println(dur);
    }

    public static void testBulkBean() {
        CACHED_BULK_BEAN.cachedBean.setPropertyValues(BEAN, CACHED_BULK_BEAN.values);

    }

    public static class CachedBulkBean {

        private Class    target     = Bean.class;
        private String[] setters    = new String[] { "setId", "setCode", "setName" };
        private String[] getters    = new String[] { "getId", "getCode", "getName" };
        private Class[]  types      = new Class[] { int.class, Integer.class, String.class };

        private Bean     bean       = new Bean();

        {
            bean.setId(1);
            bean.setCode(1);
            bean.setName("name");
        }

        public BulkBean  cachedBean = BulkBean.create(target, getters, setters, types);
        public Object[]  values     = cachedBean.getPropertyValues(bean);

    }

}
