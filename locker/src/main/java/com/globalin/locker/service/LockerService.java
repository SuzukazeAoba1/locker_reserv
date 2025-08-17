package com.globalin.locker.service;

import com.globalin.locker.domain.Locker;
import com.globalin.locker.mapper.LockerMapper;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LockerService {

    private final LockerMapper lockerMapper;

    // 리스트 모두 출력
    public List<Locker> getAllLockers() {
        return lockerMapper.selectAll();
    }

    //번호 기반 라커 검색
    public Locker getLockersByCode(Long Code) {
        return lockerMapper.selectByCode(Code);
    }

    //위치 기반 라커 검색
    public List<Locker> getLockersByLocation(String location) {
        return lockerMapper.selectByLocation(location);
    }

    @Transactional
    public boolean toggleAvailability(Long lockerCode) {
        return lockerMapper.toggleAvailability(lockerCode) == 1;
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

    // Update / Delete

    public int updateLocker(Locker locker) {
        return lockerMapper.updateLockerByCode(locker);
    }

    public int deleteLocker(long id) {
        return lockerMapper.deleteById(id);
    }
}
