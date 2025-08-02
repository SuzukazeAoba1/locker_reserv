package com.globalin.locker;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("message", "hello!");
        return "hello"; // /WEB-INF/views/hello.jsp 를 렌더링
    }
}
