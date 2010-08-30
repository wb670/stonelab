/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.stonelab.webxsample.sample.web.module.screen;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <pre>
 * обтьящй╬.
 * </pre>
 * 
 * @author Stone.J 2010-8-30 обнГ12:59:26
 */
public class Download {

    private static final String FILE_NAME = "download.xls";

    @Autowired
    private HttpServletResponse response;

    public void execute() {
        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", "attachment; filename=" + FILE_NAME);

        try {
            OutputStream out = response.getOutputStream();

            Workbook wb = getWorkbook();
            wb.write(out);

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Workbook getWorkbook() {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("gift");
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("name");
        for (int i = 1; i <= 10; i++) {
            Row r = sheet.createRow(i);
            r.createCell(0).setCellValue(i);
            r.createCell(1).setCellValue("name" + i);
        }
        return wb;
    }
}
