package com.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: LZJ
 * @Date: 2020/1/4 21:38
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AccountService accountService;

    @Override
    //场景一 无事务
//    @Transactional(propagation = Propagation.REQUIRED) //场景二、三、四、五 开启
    public void createUser(String name) {
        // 插入user 记录
        jdbcTemplate.update("INSERT INTO `user` (name) VALUES(?)", name);
        // 调用 accountService 添加帐户
        accountService.addAccount(name, 10000);
        // 人为报错 场景五开启
//        int i = 1 / 0;
    }
}
