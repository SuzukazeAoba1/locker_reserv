package com.globalin.locker.domain;

import lombok.*;

import java.sql.Timestamp;

@Data                   // ✅ Lombok: Getter/Setter, toString, equals, hashCode 자동 생성
@NoArgsConstructor      // ✅ Lombok: 매개변수 없는 기본 생성자 생성 (JSON 역직렬화 시 필수)
@AllArgsConstructor     // ✅ Lombok: 모든 필드를 매개변수로 받는 생성자 생성
@Builder                // ✅ Lombok: 빌더 패턴 메서드 자동 생성 (객체 생성 시 가독성↑)
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