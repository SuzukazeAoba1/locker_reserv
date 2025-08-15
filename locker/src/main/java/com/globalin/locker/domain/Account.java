package com.globalin.locker.domain;

import lombok.*;

import java.sql.Timestamp;

@Data                   // ✅ Lombok: Getter/Setter, toString, equals, hashCode 자동 생성
@NoArgsConstructor      // ✅ Lombok: 매개변수 없는 기본 생성자 생성 (JSON 역직렬화 시 필수)
@AllArgsConstructor     // ✅ Lombok: 모든 필드를 매개변수로 받는 생성자 생성
@Builder                // ✅ Lombok: 빌더 패턴 메서드 자동 생성 (객체 생성 시 가독성↑)
public class Account {
    private Long id;             // 시퀀스 자동 증가 PK
    private String username;     // 계정명 (문자열)
    private String password;     // 비밀번호 (문자열)
    private String role;         // 계정 권한 (ADMIN / USER)
    private String email;        // 이메일 (고정 형식)
    private String phoneNumber;  // 전화번호 (고정 형식)
    private String isActive;     // 계정 상태 (Y=활성, N=로그인 불가)
    private Timestamp createdAt; // 계정 생성일시

}
