package com.alibaba.javalab.staff;

public class Staff {

    private String name;
    private String jobNumber;
    private String joinDate;
    private String department;
    private String extension;
    private String mobile;
    private String email;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(jobNumber).append(":");
        sb.append(name).append(":");
        sb.append(joinDate).append(":");
        sb.append(department).append(":");
        sb.append(extension).append(":");
        sb.append(mobile).append(":");
        sb.append(email).append(":");
        return sb.toString();
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

}
