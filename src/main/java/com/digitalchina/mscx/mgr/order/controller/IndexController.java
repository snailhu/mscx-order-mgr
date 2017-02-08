package com.digitalchina.mscx.mgr.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping(value = {"/", "/index.do"})
    public String index(
            ModelMap map) {
        return "index"; //跳转到首页
    }
}
