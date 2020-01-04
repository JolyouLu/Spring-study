package com.test.spring;

import org.springframework.beans.factory.FactoryBean;

import java.sql.DriverManager;

/**
 * @Author: LZJ
 * @Date: 2019/12/28 21:16
 * @Version 1.0
 */
public class DriverFactoryBean implements FactoryBean {
    private String jdbcUrl;

    public Object getObject() throws Exception {
        return DriverManager.getDriver(jdbcUrl);
    }

    public Class<?> getObjectType() {
        return java.sql.Driver.class;
    }

    public boolean isSingleton() {
        return true;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }
}
