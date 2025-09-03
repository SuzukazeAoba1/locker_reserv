package com.globalin.locker.domain;

import lombok.*;

import java.sql.Timestamp;

@Data                   // ✅ Lombok: Getter/Setter, toString, equals, hashCode 자동 생성
@NoArgsConstructor      // ✅ Lombok: 매개변수 없는 기본 생성자 생성 (JSON 역직렬화 시 필수)
@AllArgsConstructor     // ✅ Lombok: 모든 필드를 매개변수로 받는 생성자 생성
@Builder                // ✅ Lombok: 빌더 패턴 메서드 자동 생성 (객체 생성 시 가독성↑)
public class Notice {
    private Long id;                // 시퀀스 자동 증가 PK
    private String title;           // 게시글 제목
    private String content;         // 게시글 내용
    private Long authorId;          // 작성자 ID (Accounts.id 참조)
    private Timestamp createdAt;    // 작성일시
    private String authorUsername;
}