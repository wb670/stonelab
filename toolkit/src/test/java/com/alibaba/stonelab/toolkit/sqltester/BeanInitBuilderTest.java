/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.sqltester;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * BeanInitBuilder单元测试.
 * 
 * @author Stone.J 2010-8-10 下午03:46:25
 */
public class BeanInitBuilderTest extends TestCase {

    private BeanInitBuilder<Bean> builder = new BeanInitBuilder<Bean>(Bean.class);

    public void testBuildOnece() {
        Bean b = builder.build();
        System.out.println(b);
        assertEquals((int) 0, b.getId());
        assertEquals((byte) 0, b.getB().byteValue());
        assertEquals((short) 0, b.getS().shortValue());
        assertEquals((int) 0, b.getI().intValue());
        assertEquals((long) 0, b.getL().longValue());
        assertEquals((float) 0, b.getF().floatValue());
        assertEquals((double) 0, b.getD().doubleValue());
        assertEquals("str", b.getStr());
        assertEquals("2000-01-01 01:01:01", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(b.getDate()));
        assertEquals(BeanEnum.ENUM_A, b.getBeanEnum());
    }

    public void testBuild() {
        List<Bean> bs = builder.build(10);
        for (int i = 0; i < 10; i++) {
            Bean b = bs.get(i);
            System.out.println(b);
            assertEquals((int) 0, b.getId());
            assertEquals((byte) 0, b.getB().byteValue());
            assertEquals((short) 0, b.getS().shortValue());
            assertEquals((int) 0, b.getI().intValue());
            assertEquals((long) 0, b.getL().longValue());
            assertEquals((float) 0, b.getF().floatValue());
            assertEquals((double) 0, b.getD().doubleValue());
            assertEquals("str" + i, b.getStr());
            assertEquals("2000-01-01 01:01:01", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(b.getDate()));
            assertEquals(BeanEnum.ENUM_A, b.getBeanEnum());
        }
    }

    /**
     * 测试Bean
     * 
     * @author Stone.J 2010-8-10 下午04:46:01
     */
    public static class Bean implements Serializable {

        private static final long serialVersionUID = -6046170733503864206L;

        private int               id;
        private Byte              b;
        private Short             s;
        private Integer           i;
        private Long              l;
        private Float             f;
        private Double            d;
        private String            str;
        private Date              date;
        private BeanEnum          beanEnum;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Byte getB() {
            return b;
        }

        public void setB(Byte b) {
            this.b = b;
        }

        public Short getS() {
            return s;
        }

        public void setS(Short s) {
            this.s = s;
        }

        public Integer getI() {
            return i;
        }

        public void setI(Integer i) {
            this.i = i;
        }

        public Long getL() {
            return l;
        }

        public void setL(Long l) {
            this.l = l;
        }

        public Float getF() {
            return f;
        }

        public void setF(Float f) {
            this.f = f;
        }

        public Double getD() {
            return d;
        }

        public void setD(Double d) {
            this.d = d;
        }

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public BeanEnum getBeanEnum() {
            return beanEnum;
        }

        public void setBeanEnum(BeanEnum beanEnum) {
            this.beanEnum = beanEnum;
        }

        @Override
        public String toString() {
            return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }

    }

    /**
     * 测试枚举
     * 
     * @author Stone.J 2010-8-10 下午04:46:11
     */
    public static enum BeanEnum {

        ENUM_A,
        ENUM_B,
        ENUM_C,
        ENUM_D,
        ENUM_E;

    }

}
