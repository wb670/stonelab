/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.misc.hibernate.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> Sep 14, 2011
 */
@Embeddable
public class Publisher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "publisher_no")
    private String            publisherId;
    private String            publisher;

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Publisher [publisherId=" + publisherId + ", publisher=" + publisher + "]";
    }

}
