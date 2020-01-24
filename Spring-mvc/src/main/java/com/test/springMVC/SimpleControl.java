package com.test.springMVC;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: LZJ
 * @Date: 2020/1/18 13:32
 * @Version 1.0
 */
//创建Controller类
public class SimpleControl implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ModelAndView mv = new ModelAndView("userView");
        mv.addObject("name","我的第一个mvc");
//        故意制作异常用于测试自定义异常机制
//        int i = 1/0;
        return mv;
    }
}
