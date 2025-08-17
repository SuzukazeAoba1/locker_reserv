package com.globalin.locker.controller;

import com.globalin.locker.domain.Account;
import com.globalin.locker.domain.Locker;
import com.globalin.locker.domain.Notice;
import com.globalin.locker.domain.Rental;
import com.globalin.locker.service.AccountService;
import com.globalin.locker.service.LockerService;
import com.globalin.locker.service.NoticeService;
import com.globalin.locker.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AccountService accountService;
    private final LockerService lockerService;
    private final RentalService rentalService;
    private final NoticeService noticeService;


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
    public String detail(@PathVariable Long code,
                         @RequestParam(required = false) String location,
                         Model model) {
        Locker locker = lockerService.getLockersByCode(code);
        model.addAttribute("locker", locker);
        // 목록으로 돌아갈 때 사용할 location (쿼리 없으면 DB 값 사용)
        model.addAttribute("backLocation", location != null ? location : locker.getLocation());
        return "admin/admin_lockers_info";
    }

    @PostMapping("/lockers/{code}/toggle")
    public String toggle(@PathVariable Long code,
                         @RequestParam String location,
                         RedirectAttributes ra) {
        boolean ok = lockerService.toggleAvailability(code);
        if (ok) ra.addFlashAttribute("msg", "状態を切り替えました。");
        else    ra.addFlashAttribute("error", "現在の状態では切り替えできません。");
        String back = "/admin/lockers/" + code + "?location=" + UriUtils.encode(location, StandardCharsets.UTF_8);
        return "redirect:" + back;
    }

    @PostMapping("/lockers/{code}/reserve")
    public String reserve(@PathVariable Long code,
                          @RequestParam Long userId,
                          @RequestParam String location,
                          RedirectAttributes ra) {
        try {
            Long rid = rentalService.reserveOrCancel(code, userId, RentalService.Action.RESERVE);
            ra.addFlashAttribute("msg", "予約が完了しました（rentalId=" + rid + "）");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "予約に失敗しました： " + e.getMessage());
        }
        return "redirect:/admin/lockers/" + code + "?location=" +
                UriUtils.encode(location, StandardCharsets.UTF_8);
    }

    @PostMapping("/lockers/{code}/cancel")
    public String cancel(@PathVariable Long code,
                         @RequestParam String location,
                         RedirectAttributes ra) {
        try {
            Long rid = rentalService.reserveOrCancel(code, null, RentalService.Action.CANCEL);
            // CANCEL は「予約キャンセル」または「使用終了」を内包
            ra.addFlashAttribute("msg", "キャンセル／終了が完了しました（rentalId=" + rid + "）");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "キャンセル／終了に失敗しました： " + e.getMessage());
        }
        return "redirect:/admin/lockers/" + code + "?location=" +
                UriUtils.encode(location, StandardCharsets.UTF_8);
    }

}
