package com.globalin.locker.Service;

import com.globalin.locker.mapper.TableMapper;
import com.globalin.locker.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MyService {

    private static final Logger logger = LoggerFactory.getLogger(MyService.class);

    private final UserMapper userMapper;
    private final TableMapper tableMapper;

    public MyService(UserMapper userMapper, TableMapper tableMapper) {
        this.userMapper = userMapper;
        this.tableMapper = tableMapper;
    }

    // 모든 테이블 조회 메소드
    public void fetchAllTables() {
        // 기존 JdbcTemplate 버전 (주석 처리)
        /*
        String sql = "SELECT table_name FROM user_tables";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        if (rows.isEmpty()) {
            System.out.println("No tables found.");
        } else {
            for (Map<String, Object> row : rows) {
                System.out.println(row.get("TABLE_NAME"));
            }
        }
        */

        // MyBatis 매퍼 사용 버전
        List<String> tables = tableMapper.getUserTables();
        if (tables == null || tables.isEmpty()) {
            System.out.println("No tables found.");
        } else {
            for (String t : tables) {
                System.out.println(t);
            }
        }
    }


    // users 테이블의 데이터 개수 조회 후, 그 다음 ID에 유저 추가
    public void fetchDataAndInsert() {
        // 기존 JdbcTemplate 버전 (주석 처리)
        /*
        String countSql = "SELECT COUNT(*) FROM users";
        Integer count = jdbcTemplate.queryForObject(countSql, Integer.class);
        int newId = count + 1;
        String name = "test_" + newId;
        String email = "test" + newId + "@example.com";
        String insertSql = "INSERT INTO users (ID, NAME, EMAIL) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertSql, newId, name, email);
        System.out.println("Inserted new user: " + name);
        */

        // MyBatis 매퍼 사용 버전
        int count = userMapper.getUserCount();
        int newId = count + 1;
        String name = "test_" + newId;
        String email = "test" + newId + "@example.com";
        userMapper.insertUser(newId, name, email);
        System.out.println("Inserted new user: " + name);
    }


    public void getData() {
        // 기존 JdbcTemplate 버전 (주석 처리)
        /*
        String sql = "SELECT * FROM users";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        if (rows.isEmpty()) {
            System.out.println("No data found.");
        } else {
            for (Map<String, Object> row : rows) {
                System.out.println(row);
            }
        }
        */

        // MyBatis 매퍼 사용 버전
        List<Map<String, Object>> users = userMapper.getAllUsers();
        if (users == null || users.isEmpty()) {
            System.out.println("No data found.");
        } else {
            for (Map<String, Object> u : users) {
                System.out.println(u);
            }
        }
    }
}
