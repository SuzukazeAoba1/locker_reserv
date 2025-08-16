package com.globalin.locker.controller;

import com.globalin.locker.domain.*;
import com.globalin.locker.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j                        // ✅ Lombok: log 객체(log.debug(), log.info() 등) 자동 생성
@Controller                   // ✅ Spring MVC: 이 클래스를 컨트롤러로 등록 (View 반환 가능)
@RequestMapping("/test")      // ✅ 기본 URL 경로 prefix 설정 → 모든 메서드 URL 앞에 "/test" 붙음
@RequiredArgsConstructor      // ✅ Lombok: final 필드나 @NonNull 필드에 대한 생성자 자동 생성 (DI 용이)
public class TestController {

    private final AccountService accountService;
    private final NoticeService noticeService;
    private final RentalService rentalService;
    private final LockerService lockerService;

    // 라커 테이블 뷰
    @GetMapping("/lockers")
    public String lockersList(Model model) {
        List<Locker> lockers = lockerService.getAllLockers();
        log.debug("lockers size={}", lockers.size());
        model.addAttribute("lockers", lockers);
        return "_test/lockers";
    }

    // ========================================
    // Accounts: Postman 테스트용 JSON CRUD
    // ========================================

    // 목록 조회
    @GetMapping(value = "/api/accounts", produces = "application/json")
    @ResponseBody
    public List<Account> apiAccountsList() {
        return accountService.getAllAccounts();
    }

    // 단건 조회 (id)
    @GetMapping(value = "/api/accounts/{id}", produces = "application/json")
    public ResponseEntity<Account> apiAccountsOne(@PathVariable long id) {
        Account a = accountService.getAccountById(id);
        return (a != null) ? ResponseEntity.ok(a) : ResponseEntity.notFound().build();
    }

    // 단건 조회 (username)
    @GetMapping(value = "/api/accounts/search", produces = "application/json")
    public ResponseEntity<Account> apiAccountsByUsername(@RequestParam String username) {
        Account a = accountService.getByUsername(username);
        return (a != null) ? ResponseEntity.ok(a) : ResponseEntity.notFound().build();
    }

    // 생성
    @PostMapping(value = "/api/accounts", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Account> apiAccountsCreate(
            @RequestBody Account account,
            org.springframework.web.util.UriComponentsBuilder ucb
    ) {
        int rows = accountService.createAccount(account); // useGeneratedKeys=true 이면 account.id 세팅됨
        if (rows == 1 && account.getId() != 0) {
            var location = ucb.path("/test/api/accounts/{id}")
                    .buildAndExpand(account.getId()).toUri();
            return ResponseEntity.created(location).body(account);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 수정 (전체 업데이트)
    @PutMapping(value = "/api/accounts/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Account> apiAccountsUpdate(@PathVariable long id, @RequestBody Account account) {
        account.setId(id);
        int rows = accountService.updateAccount(account);
        if (rows == 0) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(account);
    }

    // 삭제
    @DeleteMapping("/api/accounts/{id}")
    public ResponseEntity<Void> apiAccountsDelete(@PathVariable long id) {
        int rows = accountService.deleteAccount(id);
        if (rows == 0) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }


    // =========================
    // Lockers: Postman 테스트용 JSON CRUD
    // =========================

    // 목록
    @GetMapping(value = "/api/lockers", produces = "application/json")
    @ResponseBody
    public List<Locker> apiLockersList() {
        return lockerService.getAllLockers();
    }

    // 단건
    @GetMapping(value = "/api/lockers/{id}", produces = "application/json")
    public ResponseEntity<Locker> apiLockersOne(@PathVariable long id) {
        Locker l = lockerService.getLockerById(id);
        return (l != null) ? ResponseEntity.ok(l) : ResponseEntity.notFound().build();
    }

    // 생성
    @PostMapping(value = "/api/lockers", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Locker> apiLockersCreate(@RequestBody Locker locker,
                                                   org.springframework.web.util.UriComponentsBuilder ucb) {
        int rows = lockerService.createLocker(locker); // useGeneratedKeys=true 면 id 세팅
        if (rows == 1 && locker.getId() != 0) {
            var location = ucb.path("/test/api/lockers/{id}").buildAndExpand(locker.getId()).toUri();
            return ResponseEntity.created(location).body(locker);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 수정
    @PutMapping(value = "/api/lockers/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Locker> apiLockersUpdate(@PathVariable long id, @RequestBody Locker locker) {
        locker.setId(id);
        int rows = lockerService.updateLocker(locker);
        if (rows == 0) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(locker);
    }

    // 삭제
    @DeleteMapping("/api/lockers/{id}")
    public ResponseEntity<Void> apiLockersDelete(@PathVariable long id) {
        int rows = lockerService.deleteLocker(id);
        if (rows == 0) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }


    // =========================
    // Notices: Postman 테스트용 JSON CRUD
    // =========================

    // 목록
    @GetMapping(value = "/api/notices", produces = "application/json")
    @ResponseBody
    public List<Notice> apiNoticesList() {
        return noticeService.getAllNotices();
    }

    // 단건
    @GetMapping(value = "/api/notices/{id}", produces = "application/json")
    public ResponseEntity<Notice> apiNoticesOne(@PathVariable long id) {
        Notice n = noticeService.getNoticeById(id);
        return (n != null) ? ResponseEntity.ok(n) : ResponseEntity.notFound().build();
    }

    // 작성자별 검색
    @GetMapping(value = "/api/notices/search", produces = "application/json")
    public ResponseEntity<List<Notice>> apiNoticesByAuthor(@RequestParam long authorId) {
        List<Notice> list = noticeService.getNoticesByAuthorId(authorId);
        return ResponseEntity.ok(list);
    }

    // 생성
    @PostMapping(value = "/api/notices", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Notice> apiNoticesCreate(@RequestBody Notice notice,
                                                   org.springframework.web.util.UriComponentsBuilder ucb) {
        int rows = noticeService.createNotice(notice);
        if (rows == 1 && notice.getId() != 0) {
            var location = ucb.path("/test/api/notices/{id}").buildAndExpand(notice.getId()).toUri();
            return ResponseEntity.created(location).body(notice);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 수정
    @PutMapping(value = "/api/notices/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Notice> apiNoticesUpdate(@PathVariable long id, @RequestBody Notice notice) {
        notice.setId(id);
        int rows = noticeService.updateNotice(notice);
        if (rows == 0) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(notice);
    }

    // 삭제
    @DeleteMapping("/api/notices/{id}")
    public ResponseEntity<Void> apiNoticesDelete(@PathVariable long id) {
        int rows = noticeService.deleteNotice(id);
        if (rows == 0) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }


    // =========================
    // Rentals: Postman 테스트용 JSON CRUD
    // =========================

    // 목록
    @GetMapping(value = "/api/rentals", produces = "application/json")
    @ResponseBody
    public List<Rental> apiRentalsList() {
        return rentalService.getAllRentals();
    }

    // 단건
    @GetMapping(value = "/api/rentals/{id}", produces = "application/json")
    public ResponseEntity<Rental> apiRentalsOne(@PathVariable long id) {
        Rental r = rentalService.getRentalById(id);
        return (r != null) ? ResponseEntity.ok(r) : ResponseEntity.notFound().build();
    }

    // 사용자별
    @GetMapping(value = "/api/rentals/by-user/{userId}", produces = "application/json")
    public ResponseEntity<List<Rental>> apiRentalsByUser(@PathVariable long userId) {
        return ResponseEntity.ok(rentalService.getRentalsByUserId(userId));
    }

    // 락커별
    @GetMapping(value = "/api/rentals/by-locker/{lockerId}", produces = "application/json")
    public ResponseEntity<List<Rental>> apiRentalsByLocker(@PathVariable long lockerId) {
        return ResponseEntity.ok(rentalService.getRentalsByLockerId(lockerId));
    }

    // 생성
    @PostMapping(value = "/api/rentals", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Rental> apiRentalsCreate(@RequestBody Rental rental,
                                                   org.springframework.web.util.UriComponentsBuilder ucb) {
        int rows = rentalService.createRental(rental);
        if (rows == 1 && rental.getId() != 0) {
            var location = ucb.path("/test/api/rentals/{id}").buildAndExpand(rental.getId()).toUri();
            return ResponseEntity.created(location).body(rental);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 수정
    @PutMapping(value = "/api/rentals/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Rental> apiRentalsUpdate(@PathVariable long id, @RequestBody Rental rental) {
        rental.setId(id);
        int rows = rentalService.updateRental(rental);
        if (rows == 0) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(rental);
    }

    // 삭제
    @DeleteMapping("/api/rentals/{id}")
    public ResponseEntity<Void> apiRentalsDelete(@PathVariable long id) {
        int rows = rentalService.deleteRental(id);
        if (rows == 0) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

}
