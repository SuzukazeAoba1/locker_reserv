package com.globalin.locker.controller;

import com.globalin.locker.domain.Locker;
import com.globalin.locker.domain.Rental;
import com.globalin.locker.service.AccountService;
import com.globalin.locker.service.LockerService;
import com.globalin.locker.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final AccountService accountService;
    private final LockerService lockerService;
    private final RentalService rentalService;

    @GetMapping("/reservation/lockers/{lockerCode}")
    public String lockerInfo(Model model, @PathVariable Long lockerCode, @RequestParam(required = false) Long userId) {
        //라커 정보 가져오기
        Locker locker = lockerService.getLockersByCode(lockerCode);
        model.addAttribute("locker",locker);
        //라커의 활성 상태 가져오기
        Rental rental = rentalService.findLatestActiveByLocker(lockerCode);
        model.addAttribute("rental",rental);
        //사용 날짜
        List<LocalDateTime> availableDates = new ArrayList<>();
        LocalDateTime today = LocalDateTime.now();
        for (int i = 0; i < 7; i++) {
            availableDates.add(today.plusDays(i));
        }
        model.addAttribute("availableDates", availableDates);
        return "reservation/lockers";
    }
//라커 정보 가져오기 end

    @PostMapping("/reservation/lockers/{lockerCode}/reserve")
    public String reserve(@PathVariable("lockerCode") Long lockerCode,
                          @RequestParam Long userId,
                          @RequestParam String location,
                          RedirectAttributes ra) {
        try {
            Long rid = rentalService.reserveOrCancel(lockerCode, userId, RentalService.Action.RESERVE);
            ra.addFlashAttribute("msg", "予約が完了しました（rentalId=" + rid + "）");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "予約に失敗しました： " + e.getMessage());
        }
        return "redirect:/reservation/reservation_confirm/" + lockerCode + "?location=" +
                UriUtils.encode(location, StandardCharsets.UTF_8);
    }

    @PostMapping("/reservation/lockers/{lockerCode}/cancel")
    public String cancel(@PathVariable ("lockerCode") Long lockerCode,
                         @RequestParam String location,
                         RedirectAttributes ra) {
        try {
            Long rid = rentalService.reserveOrCancel(lockerCode, null, RentalService.Action.CANCEL);
            // CANCEL は「予約キャンセル」または「使用終了」を内包
            ra.addFlashAttribute("msg", "キャンセル／終了が完了しました（rentalId=" + rid + "）");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "キャンセル／終了に失敗しました： " + e.getMessage());
        }
        return "redirect:/reservation/reservation/" + lockerCode + "?location=" +
                UriUtils.encode(location, StandardCharsets.UTF_8);
    }


}
