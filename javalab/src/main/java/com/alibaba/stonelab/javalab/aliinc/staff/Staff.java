/*
 * Copyright 1999-2011 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.aliinc.staff;

import java.io.Serializable;

/**
 * staff info
 * 
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-1-21
 */
public class Staff implements Serializable {

    private static final long serialVersionUID = 6082519795057155562L;

    private String            name;
    private String            jobNumber;
    private String            joinDate;
    private String            department;
    private String            extension;
    private String            mobile;
    private String            email;
    private String            aliww;

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append(jobNumber).append(":");
        ret.append(name).append(":");
        ret.append(joinDate).append(":");
        ret.append(department).append(":");
        ret.append(extension).append(":");
        ret.append(mobile).append(":");
        ret.append(email).append(":");
        ret.append(aliww);
        return ret.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAliww() {
        return aliww;
    }

    public void setAliww(String aliww) {
        this.aliww = aliww;
    }

}
