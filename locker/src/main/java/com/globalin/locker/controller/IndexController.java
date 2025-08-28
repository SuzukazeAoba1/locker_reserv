package com.globalin.locker.controller;

import com.globalin.locker.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final NoticeService noticeService;

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "1") int page, Model model) {

        model.addAttribute("notices", noticeService.getNoticesPage(0,5)); // 5개만 가져오면 더 깔끔
        return "index";
    }

    @GetMapping("/error")
    public String errorPage() {
        return "error"; // /WEB-INF/views/error.jsp
    }
}
