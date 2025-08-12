package com.globalin.locker.service;

import com.globalin.locker.domain.Rental;
import com.globalin.locker.mapper.RentalMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {
    private final RentalMapper rentalMapper;

    public RentalService(RentalMapper rentalMapper) {
        this.rentalMapper = rentalMapper;
    }

    public List<Rental> getAllRentals() {
        return rentalMapper.selectAll();
    }
    
}
