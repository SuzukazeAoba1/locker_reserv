package com.globalin.locker.service;

import com.globalin.locker.domain.Account;
import com.globalin.locker.domain.Rental;
import com.globalin.locker.mapper.AccountMapper;
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

    private final AccountMapper accountMapper;
    private final LockerMapper lockerMapper;
    private final RentalMapper rentalMapper;


    public enum Action { RESERVE, START, CANCEL }

    @Transactional
    public Long reserveOrCancel(Long lockerCode, Long userId, Action action, int days) {

        switch (action) {
            case RESERVE: {
                // ✅ userId 선검증: 비어있으면 아예 진행 안 함
                if (userId == null || userId <= 0) {
                    throw new IllegalArgumentException("ユーザーIDは必須です。");
                }
                // 존재 검증
                Account acc = accountMapper.selectById(userId);
                if (acc == null) {
                    throw new IllegalArgumentException("該当ユーザーが存在しません。");
                }

                int locked = lockerMapper.updateLockerStatus(lockerCode, 2L, 1L, false); // 1->2
                if (locked == 0) {
                    throw new IllegalStateException("予約不可：利用可能（1）ではないか、競合が発生しました。");
                }
                Map<String, Object> p = new HashMap<>();
                p.put("userId", userId);
                p.put("lockerCode", lockerCode);
                p.put("days", days);

                rentalMapper.insertReservation(p);

                Object idObj = p.get("id");
                Long newId = (idObj == null) ? null : ((Number) idObj).longValue();
                if (newId == null) throw new IllegalStateException("예약 생성 실패");
                return newId;
                /*
                Map<String, Object> p = new HashMap<>();
                p.put("userId", userId);
                p.put("lockerCode", lockerCode);
                rentalMapper.insertReservation(p);

                Object idObj = p.get("id");
                Long newId = (idObj == null) ? null : ((Number) idObj).longValue();
                if (newId == null) throw new IllegalStateException("予約の作成に失敗しました。");
                return newId;
                 */
            }

            case START: {
                // 최신 예약(STATUS=1) 찾기
                Long rid = rentalMapper.findLatestReservationIdByLocker(lockerCode);
                if (rid == null) throw new IllegalStateException("有効な予約が見つかりません。");

                // RENTALS: 1 -> 2 (使用中)
                int r = rentalMapper.updateRentalStatusOnly(rid, 2L, 1L, null);
                if (r == 0) throw new IllegalStateException("予約状態が変更された可能性があります。");

                // LOCKERS: 2 -> 3 (使用中)
                int l = lockerMapper.updateLockerStatus(lockerCode, 3L, 2L, false);
                if (l == 0) throw new IllegalStateException("ロッカー状態(2→3)の更新に失敗しました。");

                return rid;
            }

            case CANCEL: {
                Long rid = rentalMapper.findLatestActiveIdByLocker(lockerCode); // STATUS IN (1,2)
                if (rid == null) throw new IllegalStateException("キャンセル／終了できる有効な履歴がありません。");

                // 1->4(予約取消) 시도, 실패 시 2->3(使用終了)
                int changed = rentalMapper.updateRentalStatusOnly(rid, 4L, 1L, null);
                if (changed == 0) changed = rentalMapper.updateRentalStatusOnly(rid, 3L, 2L, null);
                if (changed == 0) throw new IllegalStateException("キャンセル／終了に失敗しました：状態が変更された可能性があります。");

                // 활성 없으면 LOCKERS 1로 복원
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

    public Rental findLatestActiveByLocker(Long code) {
        return rentalMapper.findLatestActiveByLocker(code);
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
