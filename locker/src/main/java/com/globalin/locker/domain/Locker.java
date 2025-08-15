package com.globalin.locker.domain;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data                   // ✅ Lombok: Getter/Setter, toString, equals, hashCode 자동 생성
@NoArgsConstructor      // ✅ Lombok: 매개변수 없는 기본 생성자 생성 (JSON 역직렬화 시 필수)
@AllArgsConstructor     // ✅ Lombok: 모든 필드를 매개변수로 받는 생성자 생성
@Builder                // ✅ Lombok: 빌더 패턴 메서드 자동 생성 (객체 생성 시 가독성↑)
public class Locker {
    private Long id;             // 시퀀스 자동 증가 PK
    private String location;     // 위치명 (예: "텐진역 1층 A구역")
    private Long x;              // 열 위치 (가로 방향, 1=A열, 2=B열, 3=C열 ...)
    private Long y;              // 층 위치 (세로 방향, 1=아래층, 2=중간층, 3=위층)
    private Integer capacity;    // 크기 구분 (1=소, 2=중, 3=대)
    private Long price;          // 시간당 가격 (예: 500엔)
    private Long status;         // 상태 (1=사용 가능, 2=예약 됨, 3=사용 중, 4=점검 중)
    private Timestamp createdAt; // 생성일시
}