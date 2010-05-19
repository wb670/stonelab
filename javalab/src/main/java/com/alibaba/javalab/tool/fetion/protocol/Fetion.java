/**
 * Function: 
 * 
 * File Created at 2010-4-28
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.tool.fetion.protocol;

import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * @author li.jinl
 */
public class Fetion {

    private static final FetionHelper helper   = FetionHelper.getFetionHelper();

    private String                    mobile;
    private String                    password;

    private String                    sipcHost = "221.176.31.36";
    private String                    sipcPort = "8080";

    private LoginSession              session  = new LoginSession();

    private Socket                    socket;

    public static void main(String[] args) throws Exception {
        Fetion fetion = new Fetion("13867494321", "");
        fetion.login();
        fetion.sendMsg("hello");
    }

    public Fetion(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
        //初始化
        //init();
    }

    private void login() {
        String resource = Config.getProperties("LOGIN_RESOURCE");
        Map<String, String> param = new HashMap<String, String>();
        param.put("mobileno", mobile);
        param.put("pwd", password);
        Map<String, String> info = helper.communicate(resource, param);

        String body = info.get("body");
        String cookie = info.get("Set-Cookie");

        String tagCodePrefix = Config.getProperties("TAG_LOGIN_STATUS_CODE_PREFIX");
        String tagCodeSuffix = Config.getProperties("TAG_LOGIN_STATUS_CODE_SUFFIX");
        String code = StringUtils.substringBetween(body, tagCodePrefix, tagCodeSuffix);
        if (!StringUtils.equals(Config.getProperties("STATUS_CODE_OK"), code)) {
            return;
        }

        String tagUriPrefix = Config.getProperties("TAG_URI_PREFIX");
        String tagUriSuffix = Config.getProperties("TAG_URI_SUFFIX");
        String uri = StringUtils.substringBetween(body, tagUriPrefix, tagUriSuffix);

        String tagStatusPrefix = Config.getProperties("TAG_STATUS_PREFIX");
        String tagStatusSuffix = Config.getProperties("TAG_STATUS_SUFFIX");
        String status = StringUtils.substringBetween(body, tagStatusPrefix, tagStatusSuffix);

        String tagSidPrefix = Config.getProperties("TAG_SID_PREFIX");
        String tagSidSuffix = Config.getProperties("TAG_SID_SUFFIX");
        String sid = StringUtils.substringBetween(body, tagSidPrefix, tagSidSuffix);

        String tagSsicPrefix = Config.getProperties("TAG_SSIC_PREFIX");
        String tagSsicSuffix = Config.getProperties("TAG_SSIC_SUFFIX");
        String ssic = StringUtils.substringBetween(cookie, tagSsicPrefix, tagSsicSuffix);

        session.setLogined(true);
        session.setUri(uri);
        session.setStatus(status);
        session.setSid(sid);
        session.setSsic(ssic);
    }

    public void sendMsg(String msg) throws Exception {
        System.out.println("hello");
        if (socket == null) {
            try {
                socket = new Socket(sipcHost, Integer.valueOf(sipcPort));
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        String data = Config.getProperties("MESSAGE", session.getSid(), 0, session.getUri(), msg.length(), msg);
        System.out.println(data);
        socket.getOutputStream().write(data.getBytes());
        socket.getOutputStream().flush();

        InputStream input = socket.getInputStream();
        String ret = IOUtils.toString(input);
        System.out.println(ret);

        socket.close();
    }

    protected void init() {
        String version = Config.getProperties("CLIENT_VERSION");
        String resource = Config.getProperties("SYSTEM_CONFIG_RESOURCE");
        String data = Config.getProperties("SYSTEM_CONFIG_MOBILE", mobile, version);
        String systemConfig = helper.communicate(resource, data);

        String tagSipcPrefix = Config.getProperties("TAG_SIPC_PREFIX");
        String tagSipcSuffix = Config.getProperties("TAG_SIPC_SUFFIX");
        String split = Config.getProperties("TAG_SIPC_SPLIT");
        String sipc = StringUtils.substringBetween(systemConfig, tagSipcPrefix, tagSipcSuffix);
        String[] info = StringUtils.split(sipc, split);
        sipcHost = info[0];
        sipcPort = info[1];
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this).toString();
    }

}
