package com.globalin.locker.mapper;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Account {
    private int id;
    private String username;
    private String password;
    private String role;
    private String email;
    private String phoneNumber;
    private String isActive;
    private Timestamp createdAt;
}
