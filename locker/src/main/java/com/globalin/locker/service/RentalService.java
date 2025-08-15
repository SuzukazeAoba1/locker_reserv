package com.globalin.locker.service;

import com.globalin.locker.domain.Rental;
import com.globalin.locker.mapper.RentalMapper;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class RentalService {

    private final RentalMapper rentalMapper;

    public RentalService(RentalMapper rentalMapper) {
        this.rentalMapper = rentalMapper;
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

    public List<Rental> getRentalsByLockerId(long lockerId) {
        return rentalMapper.selectByLockerId(lockerId);
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
