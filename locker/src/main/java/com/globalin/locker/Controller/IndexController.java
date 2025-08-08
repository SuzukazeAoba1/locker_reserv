package com.globalin.locker.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        // /WEB-INF/views/index.jsp로 포워딩
        return "index";
    }

    @GetMapping("/lockers")
    public String lockers() {
        // /WEB-INF/views/index.jsp로 포워딩
        return "reservation/lockers";
    }
}
