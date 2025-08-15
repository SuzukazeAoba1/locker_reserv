package com.globalin.locker.controller;

import com.globalin.locker.domain.Locker;
import com.globalin.locker.service.LockerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final LockerService lockerService;

    public AdminController(LockerService lockerService) {
        this.lockerService = lockerService;
    }


    @GetMapping("/lockers")
    public String getLockersByLocation(@RequestParam("name") String location, Model model) {
        List<Locker> lockers = lockerService.getLockersByLocation(location);
        model.addAttribute("lockers", lockers);
        return "admin/admin_lockers";
    }

}
