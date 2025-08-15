package com.globalin.locker.domain;

import lombok.*;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@Builder
public class Rental {
    private Long id;
    private Long userId;
    private Long lockerId;
    private Timestamp startTime;
    private Timestamp endTime;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}