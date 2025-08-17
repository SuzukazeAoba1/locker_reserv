package com.globalin.locker.mapper;

import com.globalin.locker.domain.Account;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
// @Mapper는 MyBatis 매퍼 인터페이스임을 명시
// 이 인터페이스의 메서드와 XML 매퍼 파일의 SQL 구문(id)이 1:1 매칭됨
// 예: selectAll() ↔ <select id="selectAll"> ... </select>
public interface AccountMapper {

    List<Account> selectAll();
    Account selectById(long id);
    Account selectByUsername(String username);

    int insert(Account account);   // useGeneratedKeys=true면 account.id 자동 세팅
    int update(Account account);
    int deleteById(long id);
    int updateAccount(Account a);
}
