package com.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: LZJ
 * @Date: 2020/1/4 21:38
 * @Version 1.0
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(propagation = Propagation.REQUIRED) //场景一 开启
    //场景二无事务 createUser的事务会传播过来
//    @Transactional(propagation = Propagation.NOT_SUPPORTED) //场景三开启 createUser传播过来的事务挂起 addAccount方法跑一遍
//    @Transactional(propagation = Propagation.REQUIRES_NEW) //场景四、五开启 createUser传播过来的事务挂起 addAccount自己也创建一个事务
    public void addAccount(String name, int initMoney) {
        String accountid = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        jdbcTemplate.update("insert INTO account (accountName,user,money) VALUES (?,?,?)", accountid, name, initMoney);
        // 人为报错 场景五注释
        System.out.println("此处模拟addAccount事务时有报错，实验场景五需要注释");
        int i = 1 / 0;
    }
}
