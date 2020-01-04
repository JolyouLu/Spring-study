package com.test.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * @Author: LZJ
 * @Date: 2020/1/1 10:25
 * @Version 1.0
 */
public class LookupMethodSpring implements BeanFactoryAware {
    private BeanFactory beanFactory;

    public void fineSpring(){
        beanFactory.getBean(FineSpring.class);
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
