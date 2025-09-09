package com.globalin.locker.controller;


import com.globalin.locker.domain.Account;
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

import javax.servlet.http.HttpSession;
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
                          @RequestParam int days,
                          HttpSession session,
                          RedirectAttributes ra,
                          @RequestParam(required = false) String location) {

        Account loginUser = (Account) session.getAttribute("loginUser");
        if (loginUser == null) throw new IllegalStateException("로그인 정보 없음");

        Long userId = loginUser.getId(); // 세션에서 직접 가져옴

        try {
            Long rid = rentalService.reserveOrCancel(lockerCode, userId, RentalService.Action.RESERVE, days);

            Rental active = rentalService.findLatestActiveByLocker(lockerCode);
            LocalDateTime rentalDate = active.getCreatedAt().toLocalDateTime();
            LocalDateTime returnDate = rentalDate.plusDays(days);
            String formattedReturnDate = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm").format(returnDate);

            ra.addFlashAttribute("msg", "예약이 완료되었습니다 (rentalId=" + rid + ")");
            ra.addFlashAttribute("rentalId", rid);
            ra.addFlashAttribute("formattedReturnDate", formattedReturnDate);
        } catch (Exception e) {
            ra.addFlashAttribute("error", "예약에 실패했습니다: " + e.getMessage());
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
    public String myReservations(HttpSession session, Model model) {
        Account loginUser = (Account) session.getAttribute("loginUser");
        if (loginUser == null) {
            throw new IllegalStateException("로그인 정보 없음");
        }

        Long userId = loginUser.getId();


        // 1. 유저의 모든 Rental 조회
        List<Rental> myList = rentalService.getRentalsByUserId(userId);


        // 2. Rental마다 Locker 정보와 반납일 계산
        List<Map<String, Object>> reservationInfo = myList.stream().map(r -> {
            Map<String, Object> map = new HashMap<>();
            map.put("rental", r);

            // Locker 정보 가져오기
            Locker locker = lockerService.getLockersByCode(r.getLockerCode());
            map.put("locker", locker);

            // 대여일 / 반납일 계산
            if (r.getCreatedAt() != null) {
                LocalDateTime rentalDate = r.getCreatedAt().toLocalDateTime();
                int days = 7; // 기본 7일, 필요하면 Rental에 저장된 days 가져올 수도 있음
                LocalDateTime returnDate = rentalDate.plusDays(days);

                map.put("formattedStart", rentalDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
                map.put("formattedEnd", returnDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
            } else {
                map.put("formattedStart", "정보 없음");
                map.put("formattedEnd", "정보 없음");
            }

            return map;
        }).collect(Collectors.toList());
        model.addAttribute("reservations", reservationInfo);
        return "reservation/my_reservations";
    }
    // 5. 예약 취소
    @PostMapping("/lockers/{lockerCode}/cancel")
    public String cancel(@PathVariable Long lockerCode,
                         @RequestParam String userId,
                         @RequestParam(defaultValue = "0") int days,
                         RedirectAttributes ra) {
        try {
            Long rid = rentalService.reserveOrCancel(lockerCode, null, RentalService.Action.CANCEL, days);
            ra.addFlashAttribute("msg", "キャンセル／終了が完了しました（rentalId=" + rid + "）");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "キャンセル／終了に失敗しました： " + e.getMessage());
        }
        return "redirect:/reservation/my_reservations?userId=" + userId;

    }

}