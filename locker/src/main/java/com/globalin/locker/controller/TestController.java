package com.globalin.locker.controller;

import com.globalin.locker.domain.*;
import com.globalin.locker.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/test")         // 모든 라우트를 /test 아래로 격리
@RequiredArgsConstructor         // 생성자 주입 보일러플레이트 제거
public class TestController {

    private final AccountService accountService;
    private final NoticeService noticeService;
    private final RentalService rentalService;
    private final LockerService lockerService; // 오타 수정

    @GetMapping("/accounts")
    public String accountsList(Model model) {
        List<Account> accounts = accountService.getAllAccounts();
        model.addAttribute("accounts", accounts);
        return "_test/accounts/list"; // /WEB-INF/views/test/accounts/list.jsp
    }

    @GetMapping("/notices")
    public String noticesList(Model model) {
        List<Notice> notices = noticeService.getAllNotices();
        log.debug("notices size={}", notices.size());
        model.addAttribute("notices", notices);
        return "_test/notices/list"; // /WEB-INF/views/test/notices/list.jsp
    }

    @GetMapping("/rentals")
    public String rentalsList(Model model) {
        List<Rental> rentals = rentalService.getAllRentals();
        log.debug("rentals size={}", rentals.size());
        model.addAttribute("rentals", rentals);
        return "_test/rentals/list"; // /WEB-INF/views/test/rentals/list.jsp
    }

    @GetMapping("/lockers")
    public String lockersList(Model model) {
        List<Locker> lockers = lockerService.getAllLockers();
        log.debug("lockers size={}", lockers.size());
        model.addAttribute("lockers", lockers);
        return "_test/lockers/list"; // /WEB-INF/views/test/lockers/list.jsp
    }
}
