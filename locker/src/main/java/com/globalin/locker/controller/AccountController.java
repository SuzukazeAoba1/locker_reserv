package com.globalin.locker.controller;

import com.globalin.locker.domain.Account;
import com.globalin.locker.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping
    public String create(@RequestBody Account account) {
        service.createAccount(account);
        return "Account created successfully!";
    }

    @GetMapping("/{id}")
    public Account getById(@PathVariable int id) {
        return service.getAccount(id);
    }

    @GetMapping
    public List<Account> getAll() {
        return service.getAllAccounts();
    }

    @PutMapping
    public String update(@RequestBody Account account) {
        service.updateAccount(account);
        return "Account updated successfully!";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        service.deleteAccount(id);
        return "Account deleted successfully!";
    }
}
