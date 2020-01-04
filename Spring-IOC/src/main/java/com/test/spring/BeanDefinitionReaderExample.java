package com.test.spring;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.util.Arrays;

/**
 * @Author: LZJ
 * @Date: 2020/1/1 12:04
 * @Version 1.0
 */
public class BeanDefinitionReaderExample {

    public static void main(String[] args) {
        //创建一个resource装载器
        DefaultResourceLoader loader = new DefaultResourceLoader();
        //把spring.xml装载进去
        Resource resource = loader.getResource("spring.xml");
        //创建BeanDefinitionRegistry，Bean注册中心
        BeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
        //创建XmlBeanDefinitionReader，把注册中心传入
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(registry);
        //读取配置文件
        reader.loadBeanDefinitions(resource);
        System.out.println(Arrays.toString(registry.getBeanDefinitionNames()));
    }
}
