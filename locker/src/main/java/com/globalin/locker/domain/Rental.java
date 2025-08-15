package com.globalin.locker.domain;

import lombok.*;

import java.sql.Timestamp;

@Data                   // ✅ Lombok: Getter/Setter, toString, equals, hashCode 자동 생성
@NoArgsConstructor      // ✅ Lombok: 매개변수 없는 기본 생성자 생성 (JSON 역직렬화 시 필수)
@AllArgsConstructor     // ✅ Lombok: 모든 필드를 매개변수로 받는 생성자 생성
@Builder                // ✅ Lombok: 빌더 패턴 메서드 자동 생성 (객체 생성 시 가독성↑)
public class Rental {
    private Long id;             // 시퀀스 자동 증가 PK
    private Long userId;         // 사용자 ID (Accounts.id 참조)
    private Long lockerId;       // 락커 ID (Lockers.id 참조)
    private Timestamp startTime; // 사용 시작 시간
    private Timestamp endTime;   // 사용 종료 시간
    private Long status;         // 상태 (1=예약 완료, 2=사용 중, 3=사용 종료, 4=예약 취소)
    private Timestamp createdAt; // 생성일시
    private Timestamp updatedAt; // 수정일시
}