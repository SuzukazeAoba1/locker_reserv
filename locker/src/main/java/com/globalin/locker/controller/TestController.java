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

@Slf4j
@Controller
@RequestMapping("/test")         // 모든 라우트를 /test 아래로 격리
@RequiredArgsConstructor         // 생성자 주입 보일러플레이트 제거
public class TestController {

    private final AccountService accountService;
    private final NoticeService noticeService;
    private final RentalService rentalService;
    private final LockerService lockerService;

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
    public ResponseEntity<Account> apiAccountsUpdate(@PathVariable int id, @RequestBody Account account) {
        account.setId(id);
        int rows = accountService.updateAccount(account);
        if (rows == 0) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(account);
    }

    // 삭제
    @DeleteMapping("/api/accounts/{id}")
    public ResponseEntity<Void> apiAccountsDelete(@PathVariable int id) {
        int rows = accountService.deleteAccount(id);
        if (rows == 0) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }


}
