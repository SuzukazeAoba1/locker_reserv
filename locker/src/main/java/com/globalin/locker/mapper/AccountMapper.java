package com.globalin.locker.mapper;

import com.globalin.locker.domain.Account;
import java.util.List;

public interface AccountMapper {
    // Read
    List<Account> selectAll();
    Account selectById(long id);
    Account selectByUsername(String username);

    // Create / Update / Delete
    int insert(Account account);   // useGeneratedKeys=true면 account.id 자동 세팅
    int update(Account account);
    int deleteById(long id);
}
