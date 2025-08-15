package com.globalin.locker.service;

import com.globalin.locker.domain.Locker;
import com.globalin.locker.mapper.LockerMapper;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class LockerService {

    private final LockerMapper lockerMapper;

    public LockerService(LockerMapper lockerMapper) {
        this.lockerMapper = lockerMapper;
    }

    // 리스트 모두 출력
    public List<Locker> getAllLockers() {
        return lockerMapper.selectAll();
    }

    // ========================================
    // Lockers: Postman 테스트용 단순 컬럼 반환
    // ========================================

    public Locker getLockerById(long id) {
        return lockerMapper.selectById(id);
    }

    public List<Locker> getLockersPage(int offset, int limit) {
        return lockerMapper.selectPage(offset, limit);
    }

    // Create / Update / Delete
    public int createLocker(Locker locker) {
        return lockerMapper.insert(locker);
    }

    public int updateLocker(Locker locker) {
        return lockerMapper.update(locker);
    }

    public int deleteLocker(long id) {
        return lockerMapper.deleteById(id);
    }
}
