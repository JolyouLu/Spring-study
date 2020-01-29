package com.test.springAOP.dao;

import com.test.springAOP.anno.MyAnno;
import org.springframework.stereotype.Component;

/**
 * @Author: LZJ
 * @Date: 2020/1/29 16:57
 * @Version 1.0
 */
@Component
@MyAnno
public class IndexDao implements Dao{


    public void query(){
        System.out.println("dao----query");
        //模拟出异常
//        int i = 1/0;
    }
}
