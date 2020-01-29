package com.test.springAOP.test;

import com.test.springAOP.config.Appconfig;
import com.test.springAOP.dao.Dao;
import com.test.springAOP.dao.IndexDao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;



/**
 * @Author: LZJ
 * @Date: 2020/1/29 16:59
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Appconfig.class);
        Dao dao = annotationConfigApplicationContext.getBean(Dao.class);
        dao.query();
    }
}
