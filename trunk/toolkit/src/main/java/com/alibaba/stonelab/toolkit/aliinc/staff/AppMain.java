package com.alibaba.stonelab.toolkit.aliinc.staff;


public class AppMain {

    public static final StaffService staffService = new StaffService();

    public static void main(String[] args) throws Exception {
        Staff staff = staffService.getStaff("6942");
        System.out.println(staff);
    }
}
