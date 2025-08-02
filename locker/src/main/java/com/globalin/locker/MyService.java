package com.globalin.locker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class MyService {

    private static final Logger logger = LoggerFactory.getLogger(MyService.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MyService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 모든 테이블 조회 메소드
    public void fetchAllTables() {
        String sql = "SELECT table_name FROM user_tables";  // 모든 테이블 조회

        // JdbcTemplate을 사용해 쿼리 실행
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        // 결과가 없으면 No data found 메시지 출력
        if (rows.isEmpty()) {
            System.out.println("No tables found.");
        } else {
            // 데이터가 있으면 출력
            for (Map<String, Object> row : rows) {
                System.out.println(row.get("TABLE_NAME"));
            }
        }
    }

    public void getData() {
        String sql = "SELECT * FROM users";  // 쿼리문

        // JdbcTemplate을 사용해 쿼리 실행
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        // 결과가 없으면 No data found 메시지 출력
        if (rows.isEmpty()) {
            System.out.println("No data found.");
        } else {
            // 데이터가 있으면 출력
            for (Map<String, Object> row : rows) {
                System.out.println(row);  // 각 행을 출력
            }
        }
    }

    // users 테이블의 데이터 개수 조회 후, 그 다음 ID에 유저 추가
    public void fetchDataAndInsert() throws SQLException {
        // 데이터 개수를 조회하는 쿼리
        String countSql = "SELECT COUNT(*) FROM users";

        // JdbcTemplate을 사용해 쿼리 실행
        Integer count = jdbcTemplate.queryForObject(countSql, Integer.class);

        // 데이터 개수 + 1 로 새로운 ID를 설정
        int newId = count + 1;

        // `test_user + ID` 형식으로 사용자 이름 설정
        String name = "test_" + newId;

        // 이메일도 `test@id.com` 형식으로 설정
        String email = "test" + newId + "@example.com";

        // 데이터 삽입 쿼리
        String insertSql = "INSERT INTO users (ID, NAME, EMAIL) VALUES (?, ?, ?)";

        // 값 바인딩하여 쿼리 실행
        jdbcTemplate.update(insertSql, newId, name, email);

        // 수동 커밋 없이 자동 커밋이 활성화되므로 커밋 없이 실행됩니다
        System.out.println("Inserted new user: " + name);
    }
}
