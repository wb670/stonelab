/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.misc.hibernate.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author stone 2011-6-28 上午11:03:51
 */
@Entity(name = "notice")
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private int               id;
    @Column(name = "notice_title")
    private String            title;
    private String            content;
    @Column(name = "gmt_create")
    private Date              gmtCreate;
    @Column(name = "gmt_modify")
    private Date              gmtModify;
    private Publisher         publisher;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Notice [id=" + id + ", title=" + title + ", gmtCreate=" + gmtCreate + ", gmtModify=" + gmtModify
               + ", publisher=" + publisher + "]";
    }

}
