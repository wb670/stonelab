/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.aliinc.staff;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.alibaba.stonelab.toolkit.aliinc.AliHttpsAccessor;

public class StaffService {

    private static final String REQ_URL  = "https://www.cn.alibaba-inc.com/staffinfo.nsf/Search?SearchView&Query={0}&SearchOrder=4";

    private AliHttpsAccessor    accessor = new AliHttpsAccessor();

    public Staff getStaff(String jobNumber) {
        if (jobNumber == null) {
            throw new IllegalArgumentException("jobNumber is null.");
        }
        String url = MessageFormat.format(REQ_URL, jobNumber);
        String body = accessor.request(url);

        List<Staff> list = parser(body);
        if (list == null || list.isEmpty()) {
            return null;
        }

        for (Staff staff : list) {
            if (jobNumber.equals(staff.getJobNumber())) {
                return staff;
            }
        }
        return null;
    }

    public List<Staff> listStaffByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name is null.");
        }
        String enc = "";
        try {
            enc = URLEncoder.encode(name, "GBK");
        } catch (UnsupportedEncodingException e) {
        }
        String url = MessageFormat.format(REQ_URL, enc);
        String body = accessor.request(url);

        List<Staff> list = parser(body);
        List<Staff> ret = new ArrayList<Staff>();

        if (list == null || list.isEmpty()) {
            return null;
        }

        for (Staff staff : list) {
            if (name.equals(staff.getName())) {
                ret.add(staff);
            }
        }

        return ret;
    }

    public List<Staff> listStaffByDepartment(String department) {
        if (department == null) {
            throw new IllegalArgumentException("department is null.");
        }
        String enc = "";
        try {
            enc = URLEncoder.encode(department, "GBK");
        } catch (UnsupportedEncodingException e) {
        }
        String url = MessageFormat.format(REQ_URL, enc);
        String body = accessor.request(url);

        List<Staff> list = parser(body);
        return list;
    }

    private List<Staff> parser(String body) {
        List<Staff> list = new ArrayList<Staff>();
        try {
            Parser parser = Parser.createParser(body, "UTF-8");
            NodeFilter tableFilter = new NodeClassFilter(TableTag.class);
            NodeFilter cellspacing = new HasAttributeFilter("cellspacing", "0");
            NodeFilter cellpadding = new HasAttributeFilter("cellpadding", "2");
            NodeFilter border = new HasAttributeFilter("border", "0");

            AndFilter filter = new AndFilter();
            filter.setPredicates(new NodeFilter[] { tableFilter, cellspacing, cellpadding, border });
            NodeList nodes = parser.extractAllNodesThatMatch(filter);
            if (nodes.size() <= 0) {
                return null;
            }
            TableTag table = (TableTag) nodes.elementAt(0);
            TableRow[] rows = table.getRows();
            for (int i = 0; i < rows.length; i++) {
                if (i == 0) {
                    continue;
                }
                TableRow tr = rows[i];
                TableColumn[] columns = tr.getColumns();
                if (columns.length <= 13) {
                    continue;
                }
                Staff staff = new Staff();
                staff.setName(getText(columns[3]));
                staff.setDepartment(getText(columns[3]));
                staff.setJobNumber(getText(columns[1]));
                staff.setJoinDate(getText(columns[17]));
                staff.setExtension(getText(columns[9]));
                staff.setMobile(getText(columns[11]));
                staff.setEmail(getText(columns[11]));

                list.add(staff);
            }

        } catch (ParserException e) {
            System.out.println(e);
            return null;
        }
        return list;
    }

    private String getText(TableColumn column) {
        if (column.getChild(1) != null) {
            return column.getChild(1).toPlainTextString();
        }
        return "";
    }
}
