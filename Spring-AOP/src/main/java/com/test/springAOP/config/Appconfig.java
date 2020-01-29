package com.test.springAOP.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Author: LZJ
 * @Date: 2020/1/29 16:58
 * @Version 1.0
 */
@Configuration
@ComponentScan("com.test.springAOP")
@EnableAspectJAutoProxy(proxyTargetClass = true)//支持@AspectJ的语法
public class Appconfig {
}
