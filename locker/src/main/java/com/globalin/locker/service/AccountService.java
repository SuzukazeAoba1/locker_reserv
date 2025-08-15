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

    // Read
    public List<Account> getAllAccounts() {
        return accountMapper.selectAll();
    }

    public Account getAccountById(long id) {
        return accountMapper.selectById(id);
    }

    public Account getByUsername(String username) {
        return accountMapper.selectByUsername(username);
    }

    // Create / Update / Delete
    public int createAccount(Account account) {
        // MyBatis XML의 useGeneratedKeys=true 덕분에 insert 후 account.id가 채워집니다.
        return accountMapper.insert(account);
    }

    public int updateAccount(Account account) {
        return accountMapper.update(account);
    }

    public int deleteAccount(long id) {
        return accountMapper.deleteById(id);
    }

}
