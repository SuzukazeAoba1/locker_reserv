package com.globalin.locker.service;

import com.globalin.locker.domain.Account;
import com.globalin.locker.mapper.AccountMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private final AccountMapper accountMapper;

    public AccountService(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    public List<Account> getAllAccounts() {
        return accountMapper.selectAll();
    }

    public void createAccount(Account account) {
        accountMapper.insert(account);
    }

    public Account getAccount(int id) {
        return accountMapper.selectById(id);
    }

    public void updateAccount(Account account) {
        accountMapper.update(account);
    }

    public void deleteAccount(int id) {
        accountMapper.delete(id);
    }
}
