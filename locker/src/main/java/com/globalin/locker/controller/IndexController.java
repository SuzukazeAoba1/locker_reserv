package com.globalin.locker.controller;

import com.globalin.locker.domain.Account;
import com.globalin.locker.service.AccountService;
import com.globalin.locker.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final AccountService accountService;
    private final NoticeService noticeService;

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "1") int page, Model model) {

        model.addAttribute("notices", noticeService.getNoticesPage(0,5)); // 5개만 가져오면 더 깔끔
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
