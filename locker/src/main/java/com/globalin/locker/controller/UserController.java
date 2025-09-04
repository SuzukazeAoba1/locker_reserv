package com.globalin.locker.controller;

import com.globalin.locker.domain.Notice;
import com.globalin.locker.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Controller
@RequestMapping("/user")
public class UserController {

    private final NoticeService noticeService;

    @GetMapping("/notices/{id}")
    public String noticesEditForm(@PathVariable Long id,
                                  @RequestParam("page") int page,
                                  @RequestParam(required = false) String back,
                                  Model model) {
        Notice notice = noticeService.getNoticeById(id);
        if (notice == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Notice not found");
        }
        model.addAttribute("notice", notice);
        model.addAttribute("page", page);
        model.addAttribute("backUrl", (back != null && !back.isBlank()) ? back : "/");
        return "user/notices";
    }

}
