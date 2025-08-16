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
    public String getLockersByLocation(@RequestParam("location") String location, Model model) {
        List<Locker> lockers = lockerService.getLockersByLocation(location);
        model.addAttribute("lockers", lockers);
        return "admin/admin_lockers";
    }

    @GetMapping("/lockers/{code}")
    public String detail(@PathVariable String code,
                         @RequestParam(required = false) String location,
                         Model model) {
        Locker locker = lockerService.getLockersByCode(code);
        model.addAttribute("locker", locker);
        // 목록으로 돌아갈 때 사용할 location (쿼리 없으면 DB 값 사용)
        model.addAttribute("backLocation", location != null ? location : locker.getLocation());
        return "admin/admin_lockers_info";
    }

}
