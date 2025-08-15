package com.globalin.locker.controller;

import com.globalin.locker.domain.Account;
import com.globalin.locker.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService service) {
        this.accountService = service;
    }

    @GetMapping
    public List<Account> getAll() {
        return accountService.getAllAccounts();
    }

}
