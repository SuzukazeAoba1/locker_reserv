package com.globalin.locker.DB;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    private final String dbUrl;
    private final String dbUsername;
    private final String dbPassword;
    private final String walletPath;


    // 생성자에서 .env 파일을 읽어서 환경 변수로 설정
    public DatabaseConfig() {
        // .env 파일에서 환경 변수 읽기
        Dotenv dotenv = Dotenv.load();
        this.dbUrl = dotenv.get("DB_URL");
        this.dbUsername = dotenv.get("DB_USER");
        this.dbPassword = dotenv.get("DB_PASSWORD");
        this.walletPath = dotenv.get("DB_WALLET_PATH");

        // 시스템 속성으로 Oracle Wallet 경로 설정
        System.setProperty("oracle.net.wallet_location", walletPath);
    }

    // HikariDataSource 설정
    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl); // DB URL
        config.setUsername(dbUsername); // DB 사용자명
        config.setPassword(dbPassword); // DB 비밀번호
        config.setDriverClassName("oracle.jdbc.OracleDriver"); // Oracle JDBC 드라이버

        // HikariCP 커넥션 풀 설정
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setConnectionTimeout(30000);

        return new HikariDataSource(config); // 데이터소스 반환
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource); // JdbcTemplate 빈 정의
    }

}
