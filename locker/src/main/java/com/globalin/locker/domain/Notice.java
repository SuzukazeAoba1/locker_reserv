package com.globalin.locker.domain;

import lombok.*;

import java.sql.Timestamp;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Notice {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private Timestamp createdAt;
}