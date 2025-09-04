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

import javax.servlet.http.HttpSession;

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
    public String showRegisterForm() {
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

    @GetMapping("/edit")
    public String showEditForm(HttpSession session, Model model) {

        Account loginUser = (Account) session.getAttribute("loginUser"); // 로그인 시 저장한 세션 키
        if (loginUser == null) {
            return "redirect:/";
        }
        // 세션 객체 대신 DB에서 최신 데이터 재조회
        Account fresh = accountService.getByUsername(loginUser.getUsername());
        model.addAttribute("account", fresh);
        return "user/useredit";
    }

    @PostMapping("/edit")
    public String accountUpdate(@RequestParam(required = false) String password,
                       @RequestParam String email,
                       @RequestParam String phoneNumber,
                       HttpSession session,
                       RedirectAttributes ra) {

        // 1) 인증 확인
        Account loginUser = (Account) session.getAttribute("loginUser");
        if (loginUser == null) {
            ra.addFlashAttribute("errorMessage", "ログインしてください。");
            return "redirect:/";
        }

        // 2) 최신 데이터 재조회(세션 객체 직접 수정 대신 DB 기준)
        Account fresh = accountService.getByUsername(loginUser.getUsername());
        if (fresh == null) {
            ra.addFlashAttribute("errorMessage", "ユーザーが見つかりません。");
            return "redirect:/";
        }

        // 3) 제출된 필드만 갱신 (role/isActive는 변경 금지)
        fresh.setEmail(email);
        fresh.setPhoneNumber(phoneNumber);
        if (password != null && !password.isBlank()) {
            // 수업용: 평문 저장 가능하나, 실제는 BCrypt 등으로 해시 권장
            fresh.setPassword(password);
        }

        // 4) 저장 및 결과 처리
        int rows = accountService.updateAccount(fresh); // 서비스에 업데이트 메서드 구현 필요
        if (rows > 0) {
            ra.addFlashAttribute("successMessage", "更新が完了しました。");
        } else {
            ra.addFlashAttribute("errorMessage", "更新に失敗しました。");
        }
        return "redirect:/";
    }
}
