package com.alibaba.stonelab.toolkit.aliinc.staff;

import java.util.List;

import org.apache.commons.lang.StringUtils;

public class AppMain {

    public static final StaffService staffService = new StaffService();

    public static void main(String[] args) throws Exception {
        List<Staff> staffs = staffService.listStaffByDepartment("b2b-技术部-itu");
        for (Staff staff : staffs) {
            String email = staff.getEmail();
            email = StringUtils.substringBefore(email, "@");
            System.out.println(email);
        }
    }
}
