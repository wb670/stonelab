/**
 * Function: 
 * 
 * File Created at 2010-4-30
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.tool.fetion.app.plugins;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * @author li.jinl
 */
public class Weather {

    private static final String USER_AGENT  = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2 ( .NET CLR 3.5.30729)";
    private static final String LOCATION_HZ = "http://wap.weather.com.cn/wap/weather/101210101.shtml";

    private HttpClient          httpClient  = new HttpClient(new MultiThreadedHttpConnectionManager());

    protected String getWeatherInfo() {
        String content = request();
        Parser parser;
        try {
            parser = new Parser(content);
            NodeFilter weatherDiv = new NodeClassFilter(Div.class);
            NodeFilter weatherStyle = new HasAttributeFilter("class", "weather");
            NodeFilter filter = new AndFilter(new NodeFilter[] { weatherDiv, weatherStyle });
            NodeList nodes = parser.extractAllNodesThatMatch(filter);
            Node node = nodes.elementAt(0);

            String head = node.getChildren().elementAt(1).getChildren().elementAt(1).getText();
            String date = node.getChildren().elementAt(5).getChildren().elementAt(1).getChildren().elementAt(1)
                    .getChildren().elementAt(0).getText();
            String weather = node.getChildren().elementAt(5).getChildren().elementAt(1).getChildren().elementAt(3)
                    .getChildren().elementAt(0).getText();
            String wind = node.getChildren().elementAt(5).getChildren().elementAt(1).getChildren().elementAt(3)
                    .getChildren().elementAt(2).getText();

            StringBuilder sb = new StringBuilder("\n");
            sb.append(date).append(head).append(":\n");
            sb.append(weather).append("\n");
            sb.append(wind);
            return sb.toString();
        } catch (ParserException e) {
            throw new RuntimeException("WeatherInfo Error.");
        }
    }

    public String request() throws RuntimeException {
        String content = null;
        GetMethod req = null;
        try {
            req = new GetMethod(LOCATION_HZ);
            req.addRequestHeader("User-Agent", USER_AGENT);
            httpClient.executeMethod(req);
            content = req.getResponseBodyAsString();
        } catch (Exception e) {
            throw new RuntimeException("Request Error.");
        }
        req.releaseConnection();
        return content;
    }
}
