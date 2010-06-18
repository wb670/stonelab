/**
 * Function: 
 * 
 * File Created at 2010-3-5
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.misc;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.javalab.tool.db.JdbcTemplateFactory;
import com.alibaba.javalab.tool.db.JdbcTemplateFactory.DataSourceOption;

public class Test {

    public static void main(String[] args) throws Exception {
        JdbcTemplate template = JdbcTemplateFactory.getInstance(DataSourceOption.PARTY_PRODUCT);
        String sql = "select crop_email from user where department_id = 3 order by id";
        List<Map<String, String>> emails = template.queryForList(sql);
        StringBuilder sb = new StringBuilder();
        int flag = 0;
        for (Map<String, String> map : emails) {
            flag++;
            sb.append(map.get("crop_email")).append(";");
            if (flag % 50 == 0) {
                sb.append("\n");
            }
        }
        IOUtils.write(sb.toString(), new FileOutputStream("d:/tmp/emails.txt"));
    }
}
