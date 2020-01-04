package com.spring.service;

import org.springframework.stereotype.Service;

/**
 * @Author: LZJ
 * @Date: 2020/1/4 21:36
 * @Version 1.0
 */
public interface AccountService {
    void addAccount(String name, int initMoney);
}
