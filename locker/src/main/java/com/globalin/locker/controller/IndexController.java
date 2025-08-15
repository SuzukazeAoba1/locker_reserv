package com.globalin.locker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/error")
    public String errorPage() {
        return "error"; // /WEB-INF/views/error.jsp
    }
}
