package com.test.springAOP.anno;

import java.lang.annotation.*;

/**
 * @Author: LZJ
 * @Date: 2020/1/29 21:24
 * @Version 1.0
 */
//@Target(ElementType.METHOD) //该注解作用到方法上面
@Target(ElementType.TYPE) //该注解作用到类上面
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnno {
}
