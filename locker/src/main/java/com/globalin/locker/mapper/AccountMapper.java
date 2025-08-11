package com.globalin.locker.mapper;

import com.globalin.locker.domain.Account;
import java.util.List;

public interface AccountMapper {
    // Create
    void insert(Account account);

    // Read (by id)
    Account selectById(int id);

    // Read all
    List<Account> selectAll();

    // Update
    void update(Account account);

    // Delete
    void delete(int id);
}

