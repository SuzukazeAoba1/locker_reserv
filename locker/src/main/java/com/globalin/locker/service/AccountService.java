package com.globalin.locker.service;

import com.globalin.locker.domain.Account;
import com.globalin.locker.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountMapper accountMapper;


    // 리스트 모두 출력
    public List<Account> getAllAccounts() {
        return accountMapper.selectAll();
    }

    public Account getById(Long id) {
        return accountMapper.selectById(id);
    }

    @Transactional
    public int updateAccount(Account a) {
        int n = accountMapper.updateAccount(a);
        if (n == 0) throw new IllegalStateException("更新に失敗しました。");
        return n;
    }

    // ========================================
    // Accounts: Postman 테스트용 단순 컬럼 반환
    // ========================================

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

    public int deleteAccount(long id) {
        return accountMapper.deleteById(id);
    }

}
