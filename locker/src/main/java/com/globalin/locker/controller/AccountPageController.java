package com.globalin.locker.controller;

import com.globalin.locker.domain.Notice;
import com.globalin.locker.domain.Rental;
import com.globalin.locker.service.AccountService;
import com.globalin.locker.domain.Account;
import com.globalin.locker.service.NoticeService;
import com.globalin.locker.service.RentalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AccountPageController {

    private final AccountService accountService;
    private final NoticeService noticeService;
    private final RentalService rentalService;

    public AccountPageController(AccountService accountService, NoticeService noticeService, RentalService rentalService) {
        this.accountService = accountService;
        this.noticeService = noticeService;
        this.rentalService = rentalService;
    }

    @GetMapping("/accounts/list")
    public String accountsList(Model model) {
        List<Account> accounts = accountService.getAllAccounts();
        model.addAttribute("accounts", accounts);
        return "accounts/list"; // /WEB-INF/views/accounts/list.jsp
    }

    @GetMapping("/notices/list")
    public String noticesList(Model model) {
        List<Notice> notices = noticeService.getAllNotices();
        System.out.println(notices);
        model.addAttribute("notices", notices);
        return "notices/list"; // /WEB-INF/views/notices/list.jsp
    }

    @GetMapping("/rentals/list")
    public String rentalsList(Model model) {
        List<Rental> rentals = rentalService.getAllRentals();
        System.out.println(rentals);
        model.addAttribute("rentals", rentals);
        return "rentals/list"; // /WEB-INF/views/rental/list.jsp
    }

}
