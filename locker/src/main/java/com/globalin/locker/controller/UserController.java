package com.globalin.locker.controller;

import com.globalin.locker.domain.Account;
import com.globalin.locker.domain.Notice;
import com.globalin.locker.service.AccountService;
import com.globalin.locker.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Controller
@RequestMapping("/user")
public class UserController {

    private final AccountService accountService;
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

    // GET: 가입 폼 화면
    @GetMapping("/register")
    public String showForm() {
        return "user/signup";
    }

    @PostMapping("/register")
    public String register(Account account, RedirectAttributes ra) {
        // 1) 서버 강제 보정: 클라이언트 입력 무시/덮어쓰기
        account.setRole("USER");
        account.setIsActive("Y");
        account.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));

        // 2) 중복 아이디 체크
        if (accountService.getByUsername(account.getUsername()) != null) {
            ra.addFlashAttribute("errorMessage", "既に存在するIDです。");
            // 필요 시 입력값 일부만 다시 채워주기(민감정보 제외)
            ra.addFlashAttribute("tempUsername", account.getUsername());
            ra.addFlashAttribute("tempEmail", account.getEmail());
            ra.addFlashAttribute("tempPhone", account.getPhoneNumber());
            return "redirect:/user/register";
        }

        // 3) 저장 처리 (수업용: 평문 비밀번호, 실무는 반드시 해시 권장)
        int rows = accountService.createAccount(account);

        // 4) 결과 리다이렉트
        if (rows > 0) {
            ra.addFlashAttribute("successMessage", "登録が完了しました。");
            return "redirect:/";
        } else {
            ra.addFlashAttribute("errorMessage", "登録に失敗しました。");
            return "redirect:/user/register";
        }
    }


}
