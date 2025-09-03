package com.globalin.locker.controller;

import com.globalin.locker.domain.Account;
import com.globalin.locker.service.AccountService;
import com.globalin.locker.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Controller
public class IndexController {

    private final AccountService accountService;
    private final NoticeService noticeService;

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "1") int page, Model model) {


        int pageSize = 5;
        int offset = pageSize * (page-1);
        int count = noticeService.getAllNoticesCount();
        int pageBlock = 1, imsi = 1, pageCount = 1, startPage = 1, endPage = 1;


        if(count > 0){

            pageBlock = 5;
            imsi = count % pageSize == 0 ? 0 : 1;
            pageCount = count / pageSize + imsi;
            startPage = ((page - 1) / pageBlock) * pageBlock + 1;
            endPage = startPage + pageBlock - 1;

            if(endPage > pageCount) endPage = pageCount;
        }

        model.addAttribute("notices", noticeService.getNoticesPage(offset,pageSize)); // 5개만 가져오면 더 깔끔
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("pageBlock", pageBlock);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);


        return "index";
    }

    @PostMapping("/login")
    public String login(@RequestParam String loginId,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes ra){

        Account account = accountService.getByUsername(loginId);

        if(account == null || !password.equals(account.getPassword())){
            ra.addAttribute("error", "1"); // 쿼리스트링에 error=1
            return "redirect:/";           // index.jsp로
        }

        // 성공 → 세션 저장 후 메인으로
        session.setAttribute("loginUser", account);
        session.setMaxInactiveInterval(60 * 30); // 30분
        return "redirect:/";

    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/error")
    public String errorPage() {
        return "error"; // /WEB-INF/views/error.jsp
    }
}
