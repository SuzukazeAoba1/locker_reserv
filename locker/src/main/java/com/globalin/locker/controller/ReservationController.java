package com.globalin.locker.controller;

import com.globalin.locker.service.AccountService;
import com.globalin.locker.service.LockerService;
import com.globalin.locker.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final AccountService accountService;
    private final LockerService lockerService;
    private final RentalService rentalService;

    


}
