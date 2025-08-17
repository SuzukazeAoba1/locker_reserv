package com.globalin.locker.mapper;

import com.globalin.locker.domain.Rental;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
// @Mapper는 MyBatis 매퍼 인터페이스임을 명시
// 이 인터페이스의 메서드와 XML 매퍼 파일의 SQL 구문(id)이 1:1 매칭됨
// 예: selectAll() ↔ <select id="selectAll"> ... </select>
public interface RentalMapper {

    List<Rental> selectAll();

    Rental selectById(@Param("id") long id);
    Rental findLatestActiveByLocker(@Param("lockerCode") Long lockerCode);

    List<Rental> selectByUserId(@Param("userId") long userId);

    List<Rental> selectByLockerCode(@Param("lockerCode") long lockerCode);

    List<Rental> selectPage(@Param("offset") int offset,
                            @Param("limit") int limit);

    int updateRentalStatusOnly(
            @Param("rentalId")      Long rentalId,
            @Param("toStatus")      Long toStatus,
            @Param("fromStatus")    Long fromStatus,       // null OK
            @Param("fromStatusList") List<Long> fromStatusList // null OK
    );

    Long findLatestActiveIdByLocker(@Param("lockerCode") Long lockerCode);
    Long findLatestReservationIdByLocker(@Param("lockerCode") Long lockerCode);

    int insertReservation(Map<String, Object> p);

    int insert(Rental rental);

    int update(Rental rental);

    int deleteById(@Param("id") long id);
}
