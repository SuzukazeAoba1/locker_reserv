package com.globalin.locker.mapper;

import com.globalin.locker.domain.Locker;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
// @Mapper는 MyBatis 매퍼 인터페이스임을 명시
// 이 인터페이스의 메서드와 XML 매퍼 파일의 SQL 구문(id)이 1:1 매칭됨
// 예: selectAll() ↔ <select id="selectAll"> ... </select>
public interface LockerMapper {

    List<Locker> selectAll();
    Locker selectById(@Param("id") long id);
    List<Locker> selectPage(@Param("offset") int offset,
                            @Param("limit") int limit);

    int insert(Locker locker);
    int update(Locker locker);
    int deleteById(@Param("id") long id);
}
