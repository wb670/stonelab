/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.cl;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2013-7-9
 */
public class ClBean1 {

    private ClBean2 bean2;

    private int     id;
    private String  name;

    public ClBean1(){
        this.id = 1;
        this.name = "ClBean1";
        this.bean2 = new ClBean2();
    }

    public ClBean2 getBean2() {
        return bean2;
    }

    public void setBean2(ClBean2 bean2) {
        this.bean2 = bean2;
    }

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
        return "ClBean1 [bean2=" + bean2 + ", id=" + id + ", name=" + name + "]";
    }

}
