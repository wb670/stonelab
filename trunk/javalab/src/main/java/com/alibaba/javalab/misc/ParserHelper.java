/**
 * Function: 
 * 
 * File Created at 2010-3-22
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.misc;

import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;

/**
 * @author li.jinl
 */
public class ParserHelper {

    public static final ParserHelper helper     = new ParserHelper();

    public static final String       ENCODING   = "UTF8";
    public static final String       URL        = "http://b2b-doc.alibaba-inc.com/pages/viewpage.action?pageId=40950165";
    public static final String       FILE       = "d:/tmp/tmp.txt";
    public static final String       COOKIE     = "seraph.confluence=fVdUaKNrJjOmFrJLUqedKFIMOc1Pn31hNpjKpXT4rrGsnBMw";

    private HttpClient               httpClient = new HttpClient();

    public static void main(String[] args) throws Exception {
        helper.parse(helper.readHttp(URL, ENCODING, COOKIE));
    }

    public void parse(String content) throws Exception {
        Parser parser = new Parser(content);
        NodeList list = parser.extractAllNodesThatMatch(new NodeClassFilter(TableTag.class));
        TableTag table = (TableTag) list.elementAt(6);
        TableRow[] rows = table.getRows();
        for (TableRow row : rows) {
            TableColumn[] columns = row.getColumns();
            if (columns.length < 7) {
                continue;
            }
            String flag = columns[0].getStringText().trim().replaceAll("(<.*>|&nbsp;)*", "");
            String task = columns[1].getStringText().trim().replaceAll("(<.*>|&nbsp;)*", "");
            String time = columns[3].getStringText().trim().replaceAll("(<.*>|&nbsp;)*", "");
            String person = columns[4].getStringText().trim().replaceAll("(<.*>|&nbsp;)*", "");

            if (!("完成".equals(flag)) && time.contains("24")) {
                System.out.println(task + ":" + person + ":" + time);
            }

        }
    }

    public String readFile(String file, String encoding) throws Exception {
        InputStreamReader in = new InputStreamReader(new FileInputStream(file), encoding);
        String content = IOUtils.toString(in);
        IOUtils.closeQuietly(in);
        return content;
    }

    public String readHttp(String url, String encoding, String cookie) throws Exception {
        GetMethod req = new GetMethod(url);
        req.addRequestHeader("Cookie", cookie);
        httpClient.executeMethod(req);
        InputStreamReader in = new InputStreamReader(req.getResponseBodyAsStream(), encoding);
        String content = IOUtils.toString(in);
        IOUtils.closeQuietly(in);
        req.releaseConnection();
        return content;
    }
}
