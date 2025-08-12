package com.globalin.locker.domain;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Locker {
    private Long id;
    private String location;
    private Long x;
    private Long y;
    private Integer capacity;
    private Long price;
    private String status;
    private LocalDateTime createdAt;
}