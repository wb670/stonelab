package com.alibaba.javalab.staff;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class AppMain {

    private static final String JOBNUMBER_FILE = "d:/tmp/ids.txt";
    private static final String EMAIL_FILE     = "d:/tmp/emails2.txt";

    private static StaffService staffService   = new StaffService();

    public static void main(String[] args) {
        try {
            AppMain.validate();
            AppMain.info();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void validate() throws Exception {
        if (!new File(JOBNUMBER_FILE).exists()) {
            throw new Exception("当前目录不存在ids.txt文件!");
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

    public static void info() throws Exception {
        List<String> jobNumbers = getJobNumbers();
        List<String> info = new ArrayList(jobNumbers.size());
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
            String value = staff.getJobNumber() + " " + staff.getEmail();
            info.add(value);
        }
        save(info);
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
