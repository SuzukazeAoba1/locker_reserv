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

    //위치 기반 로커 검색
    List<Locker> selectByLocation(String location);


    List<Locker> selectAll();
    Locker selectById(@Param("id") long id);
    Locker selectByCode(@Param("lockerCode") Long lockerCode);
    List<Locker> selectPage(@Param("offset") int offset,
                            @Param("limit") int limit);

    int updateLockerStatus(
            @Param("lockerCode") Long lockerCode,
            @Param("toStatus")   Long toStatus,
            @Param("fromStatus") Long fromStatus,       // null 허용
            @Param("requireNoActive") boolean requireNoActive
    );

    int toggleAvailability(@Param("lockerCode") Long lockerCode);
    int insert(Locker locker);
    int updateLockerByCode(Locker locker);
    int deleteById(@Param("id") long id);
}
