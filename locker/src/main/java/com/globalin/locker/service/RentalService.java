package com.globalin.locker.service;

import com.globalin.locker.domain.Rental;
import com.globalin.locker.mapper.LockerMapper;
import com.globalin.locker.mapper.RentalMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final LockerMapper lockerMapper;
    private final RentalMapper rentalMapper;

    public enum Action { RESERVE, CANCEL }

    @Transactional
    public Long reserveOrCancel(Long lockerCode, Long userId, Action action) {
        switch (action) {
            case RESERVE: {
                // LOCKERS: 1 -> 2 (조건부)
                int locked = lockerMapper.updateLockerStatus(lockerCode, 2L, 1L, false);
                if (locked == 0) {
                    throw new IllegalStateException("予約不可：利用可能（1）ではないか、競合が発生しました。");
                }

                // RENTALS: 예약 레코드 생성 (selectKey BEFORE로 ID 채움)
                Map<String, Object> p = new HashMap<>();
                p.put("userId", userId);
                p.put("lockerCode", lockerCode);
                rentalMapper.insertReservation(p);

                Object idObj = p.get("id");
                Long newId = (idObj == null) ? null : ((Number) idObj).longValue();
                if (newId == null) {
                    throw new IllegalStateException("予約の作成に失敗しました。");
                }
                return newId;
            }

            case CANCEL: {
                // (선택) 사용자 소유 검증을 하려면 userId도 조건에 포함하는 find를 쓰세요.
                Long rid = rentalMapper.findLatestActiveIdByLocker(lockerCode);
                if (rid == null) {
                    throw new IllegalStateException("キャンセル／終了できる有効な履歴がありません。");
                }

                // RENTALS: 1 -> 4(예약취소) 시도, 실패 시 2 -> 3(사용종료) 시도
                int changed = rentalMapper.updateRentalStatusOnly(rid, 4L, 1L, null);
                if (changed == 0) {
                    changed = rentalMapper.updateRentalStatusOnly(rid, 3L, 2L, null);
                }
                if (changed == 0) {
                    // 이미 완료/취소되었거나, 경쟁 상태로 fromStatus 불일치
                    throw new IllegalStateException("キャンセル／終了に失敗しました：状態が変更された可能性があります。");
                }

                // LOCKERS: 활성(1,2) 없으면 1로 복원
                lockerMapper.updateLockerStatus(lockerCode, 1L, null, true);
                return rid;
            }

            default:
                throw new IllegalArgumentException("不明な操作: " + action);
        }
    }


    // 리스트 모두 출력
    public List<Rental> getAllRentals() {
        return rentalMapper.selectAll();
    }

    // ========================================
    // Rentals: Postman 테스트용 단순 컬럼 반환
    // ========================================

    public Rental getRentalById(long id) {
        return rentalMapper.selectById(id);
    }


    public List<Rental> getRentalsByUserId(long userId) {
        return rentalMapper.selectByUserId(userId);
    }

    public List<Rental> getRentalsByLockerId(long lockerCode) {
        return rentalMapper.selectByLockerCode(lockerCode);
    }

    public List<Rental> getRentalsPage(int offset, int limit) {
        return rentalMapper.selectPage(offset, limit);
    }

    // Create / Update / Delete
    public int createRental(Rental rental) {
        return rentalMapper.insert(rental);
    }

    public int updateRental(Rental rental) {
        return rentalMapper.update(rental);
    }

    public int deleteRental(long id) {
        return rentalMapper.deleteById(id);
    }
}
