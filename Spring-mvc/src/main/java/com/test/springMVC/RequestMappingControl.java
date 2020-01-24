package com.test.springMVC;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: LZJ
 * @Date: 2020/1/23 21:44
 * @Version 1.0
 */
@Controller
public class RequestMappingControl {

    @RequestMapping("/hello.do")
    public ModelAndView hell0(){
        ModelAndView mv = new ModelAndView("userView");
        mv.addObject("name","我的第一个@RequestMapping的mvc");
        return mv;
    }
}
