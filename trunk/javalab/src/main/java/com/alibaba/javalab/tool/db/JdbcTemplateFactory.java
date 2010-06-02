/**
 * Function: 
 * 
 * File Created at 2010-3-4
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.tool.db;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.gjt.mm.mysql.Driver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.alibaba.china.jdbc.SimpleDriver;

/**
 * @author li.jinl
 */
public class JdbcTemplateFactory {

    private static final Map<DataSourceOption, JdbcTemplate> TEMPLATES = new HashMap<DataSourceOption, JdbcTemplate>();

    public static final JdbcTemplate getInstance(DataSourceOption option) {
        if (!TEMPLATES.containsKey(option)) {
            TEMPLATES.put(option, new JdbcTemplate(DataSourceFactory.getInstance(option)));
        }
        return TEMPLATES.get(option);
    }

    public static class DataSourceFactory {

        private static final Map<DataSourceOption, DataSource> SOURCES = new HashMap<DataSourceOption, DataSource>();

        public static final DataSource getInstance(DataSourceOption option) {
            if (!SOURCES.containsKey(option)) {
                DataSource dataSource = new SimpleDriverDataSource(option.driverOption.driver, option.url,
                        option.username, option.password);
                if (option.properties != null) {
                    ((SimpleDriverDataSource) dataSource).setConnectionProperties(option.properties);
                }
                SOURCES.put(option, dataSource);
            }
            return SOURCES.get(option);
        }
    }

    public static enum DataSourceOption {

        ORACLE_OCN_TEST(DriverOption.ORACLE, "jdbc:oracle:thin:@10.20.36.19:1521:ocntest", "alibaba", "ccbutest",
                getAlibabaProperties()),
        MYSQL_210(DriverOption.MYSQL, "jdbc:mysql://10.20.131.210/javalab", "root", "123"),
        PARTY_PRODUCT(DriverOption.MYSQL, "jdbc:mysql://10.20.131.207:3306/alibaba_party", "party", "hello1234");

        public DriverOption driverOption;
        public String       url;
        public String       username;
        public String       password;
        public Properties   properties;

        private DataSourceOption(DriverOption driverOption, String url, String username, String password) {
            this(driverOption, url, username, password, null);
        }

        private DataSourceOption(DriverOption driverOption, String url, String username, String password,
                                 Properties properties) {
            this.driverOption = driverOption;
            this.url = url;
            this.username = username;
            this.password = password;
            this.properties = properties;
        }

        private static Properties getAlibabaProperties() {
            Properties p = new Properties();
            p.put("clientEncoding", "GBK");
            p.put("serverEncoding", "ISO-8859-1");
            return p;
        }

    }

    public static enum DriverOption {

        ORACLE,
        MYSQL;

        static {
            try {
                DriverOption.ORACLE.driver = new SimpleDriver();
                DriverOption.MYSQL.driver = new Driver();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }

        public java.sql.Driver driver;

    }

}
