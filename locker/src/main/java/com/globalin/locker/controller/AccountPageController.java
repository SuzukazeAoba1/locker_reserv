package com.globalin.locker.controller;

import com.globalin.locker.service.AccountService;
import com.globalin.locker.domain.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AccountPageController {

    private final AccountService accountService;

    public AccountPageController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/accounts/list")
    public String list(Model model) {
        List<Account> accounts = accountService.getAllAccounts();
        model.addAttribute("accounts", accounts);
        return "accounts/list"; // /WEB-INF/views/accounts/list.jsp
    }
}
