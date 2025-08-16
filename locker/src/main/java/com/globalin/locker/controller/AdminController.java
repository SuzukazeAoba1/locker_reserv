package com.globalin.locker.controller;

import com.globalin.locker.domain.Account;
import com.globalin.locker.domain.Locker;
import com.globalin.locker.domain.Notice;
import com.globalin.locker.domain.Rental;
import com.globalin.locker.service.AccountService;
import com.globalin.locker.service.LockerService;
import com.globalin.locker.service.NoticeService;
import com.globalin.locker.service.RentalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AccountService accountService;
    private final LockerService lockerService;
    private final RentalService rentalService;
    private final NoticeService noticeService;

    public AdminController(AccountService accountService, NoticeService noticeService, RentalService rentalService, LockerService lockerService) {
        this.accountService = accountService;
        this.lockerService = lockerService;
        this.rentalService = rentalService;
        this.noticeService = noticeService;
    }

    @GetMapping("/accounts")
    public String accountsList(Model model) {
        List<Account> accounts = accountService.getAllAccounts();
        model.addAttribute("accounts", accounts);
        return "admin/admin_users";
    }

    @GetMapping("/notices")
    public String noticesList(Model model) {
        List<Notice> notices = noticeService.getAllNotices();
        model.addAttribute("notices", notices);
        return "admin/admin_notices";
    }

    @GetMapping("/rentals")
    public String rentalsList(Model model) {
        List<Rental> rentals = rentalService.getAllRentals();
        model.addAttribute("rentals", rentals);
        return "admin/admin_rentals";
    }

    @GetMapping("/lockers")
    public String getLockersByLocation(@RequestParam("location") String location, Model model) {
        List<Locker> lockers = lockerService.getLockersByLocation(location);
        model.addAttribute("lockers", lockers);
        return "admin/admin_lockers";
    }

    @GetMapping("/lockers/{code}")
    public String detail(@PathVariable String code,
                         @RequestParam(required = false) String location,
                         Model model) {
        Locker locker = lockerService.getLockersByCode(code);
        model.addAttribute("locker", locker);
        // 목록으로 돌아갈 때 사용할 location (쿼리 없으면 DB 값 사용)
        model.addAttribute("backLocation", location != null ? location : locker.getLocation());
        return "admin/admin_lockers_info";
    }

}
