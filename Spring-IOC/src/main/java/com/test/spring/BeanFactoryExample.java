package com.test.spring;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.util.Arrays;

/**
 * @Author: LZJ
 * @Date: 2020/1/1 12:49
 * @Version 1.0
 */
public class BeanFactoryExample {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.setResourceLoader(new DefaultResourceLoader());
        reader.loadBeanDefinitions("spring.xml");
        beanFactory.getBean("helloSpring");
        System.out.println(Arrays.toString(beanFactory.getBeanDefinitionNames()));

    }
}
