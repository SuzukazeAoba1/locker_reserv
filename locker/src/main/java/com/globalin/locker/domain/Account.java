package com.globalin.locker.domain;

import lombok.*;

import java.sql.Timestamp;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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
