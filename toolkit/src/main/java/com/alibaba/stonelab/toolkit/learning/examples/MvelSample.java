/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.learning.examples;

import java.util.HashMap;
import java.util.Map;

import org.mvel2.MVEL;

/**
 * @author Stone.J 2010-8-13 下午04:27:42
 */
public class MvelSample {

    public static void main(String[] args) {
        Object result = null;
        Map<String, Object> ctx = new HashMap<String, Object>();
        //执行
        ctx.put("value", 99);
        result = MVEL.eval("value + 1", ctx);
        System.out.println(result);

        MvelBean bean = new MvelBean();
        MVEL.setProperty(bean, "id", 1);
        MVEL.setProperty(bean, "name", "name");
        MVEL.setProperty(bean, "area.id", 1);
        MVEL.setProperty(bean, "area.name", "name");
        System.out.println(bean);
    }

    public static class MvelBean {

        private int    id;
        private String name;
        private Area   area = new Area();

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Area getArea() {
            return area;
        }

        public void setArea(Area area) {
            this.area = area;
        }

        @Override
        public String toString() {
            return "MvelBean [area=" + area + ", id=" + id + ", name=" + name + "]";
        }

    }

    public static class Area {

        private int    id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Area [id=" + id + ", name=" + name + "]";
        }

    }

}
