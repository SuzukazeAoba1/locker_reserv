package com.globalin.locker.controller;


import com.globalin.locker.domain.Locker;
import com.globalin.locker.domain.Rental;
import com.globalin.locker.service.AccountService;
import com.globalin.locker.service.LockerService;
import com.globalin.locker.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Controller
@RequestMapping("/reservation")
public class ReservationController {

    private final AccountService accountService;
    private final LockerService lockerService;
    private final RentalService rentalService;


    // 1. 라커 목록 (admin_lockers_list.jsp)
    @GetMapping("/lockers")
    public String lockersByLocation(Model model, @RequestParam String location) {
        List<Locker> lockers = lockerService.getLockersByLocation(location);
        model.addAttribute("lockers", lockers);
        model.addAttribute("location", location);
        return "reservation/lockers";
    }

    // 2. 라커 상세 + 예약 화면 (reservation.jsp)
    @GetMapping("/lockers/{lockerCode}")
    public String lockerDetail(@PathVariable Long lockerCode,
                               @RequestParam(required = false) Long userId,
                               @RequestParam(required = false) String location,
                               Model model) {
        Locker locker = lockerService.getLockersByCode(lockerCode);
        model.addAttribute("locker", locker);

        Rental rental = rentalService.findLatestActiveByLocker(lockerCode);
        model.addAttribute("rental", rental);

        List<LocalDateTime> availableDates = new ArrayList<>();
        LocalDateTime today = LocalDateTime.now();
        for (int i = 0; i < 7; i++) {
            availableDates.add(today.plusDays(i));
        }
        model.addAttribute("availableDates", availableDates);
        model.addAttribute("location", location != null ? location : locker.getLocation());

        return "reservation/reservation";
    }

    // 3. 예약 처리 후 확인 페이지 (reservation_confirm.jsp)
    @PostMapping("/lockers/{lockerCode}/reserve")
    public String reserve(@PathVariable Long lockerCode,
                          @RequestParam Long userId,
                          @RequestParam int days,
                          RedirectAttributes ra,
                          @RequestParam(required = false) String location) {

        Locker locker = lockerService.getLockersByCode(lockerCode);
        try {
            Long rid = rentalService.reserveOrCancel(lockerCode, userId, RentalService.Action.RESERVE);
            Rental active = rentalService.findLatestActiveByLocker(lockerCode);
            LocalDateTime rentalDate = active.getCreatedAt().toLocalDateTime();
            LocalDateTime returnDate = rentalDate.plusDays(days);
            String formattedReturnDate = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm").format(returnDate);

            ra.addFlashAttribute("msg", "予約が完了しました（rentalId=" + rid + "）");
            ra.addFlashAttribute("rentalId", rid);
            ra.addFlashAttribute("formattedReturnDate", formattedReturnDate);
        } catch (Exception e) {
            ra.addFlashAttribute("error", "予約に失敗しました： " + e.getMessage());
        }
        return "redirect:/reservation/reservation_confirm/" + lockerCode + "?location=" +
                UriUtils.encode(location, StandardCharsets.UTF_8);
    }

    @GetMapping("/reservation_confirm/{lockerCode}")
    public String reservationConfirm(@PathVariable Long lockerCode,
                                     @RequestParam String location,
                                     Model model) {
        Locker locker = lockerService.getLockersByCode(lockerCode);
        if (locker.getStatus() != null && (locker.getStatus() == 2L || locker.getStatus() == 3L)) {
            Rental active = rentalService.findLatestActiveByLocker(lockerCode); // 주입 필요
            model.addAttribute("activeRental", active);

        }
        model.addAttribute("locker",locker);
        model.addAttribute("lockerCode", lockerCode);
        model.addAttribute("location", location);
        return "reservation/reservation_confirm";
    }

    // 4. 내 예약 목록 (my_reservations.jsp)
    @GetMapping("/my_reservations")
    public String myReservations(@RequestParam Long userId, @RequestParam(required = false) Integer days, Model model) {
        List<Rental> myList = rentalService.getRentalsByUserId(userId); // 로그인 유저 기준

        // 각 Rental마다 계산된 반납일 담기
        List<Map<String, Object>> reservationInfo = myList.stream().map(r -> {
            Map<String, Object> map = new HashMap<>();
            map.put("rental", r);
            if (days != null && r.getCreatedAt() != null) {
                LocalDateTime rentalDate = r.getCreatedAt().toLocalDateTime();
                LocalDateTime returnDate = rentalDate.plusDays(days);
                String formattedReturnDate = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm").format(returnDate);
                map.put("formattedReturnDate", formattedReturnDate);
            } else {
                map.put("formattedReturnDate", "정보 없음");
            }
            return map;
        }).collect(Collectors.toList());

        model.addAttribute("reservations", reservationInfo);

        return "reservation/my_reservations";
    }
    /*
    @GetMapping("/my_reservations")
    public String myReservations(@RequestParam Long lockerCode, Model model, @RequestParam int days, @RequestParam Long userId) {
        List<Rental> myList = rentalService.getRentalsByUserId(userId); // 로그인 유저 기준
        model.addAttribute("reservations", myList);
        //List<LocalDateTime> availableDates = new ArrayList<>();
        Rental active = rentalService.findLatestActiveByLocker(lockerCode);
        if (active != null) {
            LocalDateTime rentalDate = active.getCreatedAt().toLocalDateTime();
            LocalDateTime returnDate = rentalDate.plusDays(days);
            String formattedReturnDate = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm").format(returnDate);
            model.addAttribute("formattedReturnDate", formattedReturnDate);
        } else {
            model.addAttribute("formattedReturnDate", "정보 없음");
        }
        /*
        LocalDateTime rentalDate = active.getCreatedAt().toLocalDateTime();
        LocalDateTime returnDate = rentalDate.plusDays(days);
        String formattedReturnDate = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm").format(returnDate);
        model.addAttribute("formattedReturnDate", formattedReturnDate);
        */

/*
        LocalDateTime today = LocalDateTime.now();
        for (int i = 0; i < 7; i++) {
            availableDates.add(today.plusDays(i));
        }
        model.addAttribute("availableDates", availableDates);
        */
    /*
        return "reservation/my_reservations";
    }
*/
    // 5. 예약 취소
    @PostMapping("/lockers/{lockerCode}/cancel")
    public String cancel(@PathVariable Long lockerCode,
                         @RequestParam String location,
                         RedirectAttributes ra) {
        try {
            Long rid = rentalService.reserveOrCancel(lockerCode, null, RentalService.Action.CANCEL);
            ra.addFlashAttribute("msg", "キャンセル／終了が完了しました（rentalId=" + rid + "）");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "キャンセル／終了に失敗しました： " + e.getMessage());
        }
        return "redirect:/reservation/lockers/" + lockerCode + "?location=" +
                UriUtils.encode(location, StandardCharsets.UTF_8);

    }
    //6. 사용 기간 계산
    /*
    @PostMapping("/lockers/{lockerCode}/countDate")
    public String countDate(@PathVariable Long lockerCode,@RequestParam int days, Model model ){
        Locker locker = lockerService.getLockersByCode(lockerCode);
        if (locker.getStatus() != null && (locker.getStatus() == 2L || locker.getStatus() == 3L)) {
            Rental active = rentalService.findLatestActiveByLocker(lockerCode); // 주입 필요
            model.addAttribute("activeRental", active);
            LocalDateTime rentalDate = active.getCreatedAt().toLocalDateTime();
            LocalDateTime returnDate= rentalDate.plusDays(days);
            String formattedReturnDate = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm").format(returnDate);
            model.addAttribute("formattedReturnDate", formattedReturnDate);

            return "reservation/my_reservation";
        }
        return "";
    }
*/

}