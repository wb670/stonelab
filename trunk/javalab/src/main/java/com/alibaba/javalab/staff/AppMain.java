package com.alibaba.javalab.staff;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class AppMain {

    private static final String JOBNUMBER_FILE = "/home/alibaba/tmp/name.txt";
    private static final String EMAIL_FILE     = "/home/alibaba/tmp/email.txt";

    private static StaffService staffService   = new StaffService();

    public static void main(String[] args) throws Exception {
        List<Staff> list = staffService.listStaffByDepartment("b2b-技术部-中国网站技术部-收费开发部");
        for (Staff staff : list) {
            System.out.println(staff);
        }
    }

    public static void emails() throws Exception {
        List<String> jobNumbers = getJobNumbers();
        List<String> emails = new ArrayList<String>(jobNumbers.size());
        for (String jobNumber : jobNumbers) {
            Staff staff = staffService.getStaff(jobNumber.trim());
            if (staff == null) {
                System.out.println("staff:" + jobNumber);
                continue;
            }
            if (staff.getEmail() == null) {
                System.out.println("email:" + jobNumber);
                continue;
            }
            emails.add(staff.getEmail());
        }
        save(emails);
    }

    private static void save(List<String> emails) throws Exception {
        FileOutputStream out = new FileOutputStream(EMAIL_FILE);
        IOUtils.writeLines(emails, "\n", out);
        out.close();
    }

    private static List<String> getJobNumbers() throws Exception {
        FileInputStream in = new FileInputStream(JOBNUMBER_FILE);
        List<String> list = IOUtils.readLines(in);
        in.close();
        return list;
    }

}
