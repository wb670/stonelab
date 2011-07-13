package com.alibaba.stonelab.javalab.misc.hibernate.model;

import java.util.Date;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class DateInfo {

    private Date gmt_create;
    private Date gmt_modify;

    public Date getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(Date gmt_create) {
        this.gmt_create = gmt_create;
    }

    public Date getGmt_modify() {
        return gmt_modify;
    }

    public void setGmt_modify(Date gmt_modify) {
        this.gmt_modify = gmt_modify;
    }

}
