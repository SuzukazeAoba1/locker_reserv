package com.globalin.locker.service;

import com.globalin.locker.domain.Locker;
import com.globalin.locker.mapper.LockerMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LockerService {

    private final LockerMapper lockerMapper;

    public LockerService(LockerMapper lockerMapper) {
        this.lockerMapper = lockerMapper;
    }

    public List<Locker> getAllLockers() {
        return lockerMapper.selectAll();
    }
}
