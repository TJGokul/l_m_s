package com.example.library_management_system;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.net.URI;

@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.url:jdbc:postgresql://localhost:5432/library_db}")
    private String defaultDbUrl;

    @Value("${spring.datasource.username:postgres}")
    private String defaultUsername;

    @Value("${spring.datasource.password:postgres}")
    private String defaultPassword;

    @Bean
    @Primary
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();

        // 1. Check for standard cloud environment variables
        String rawUrl = System.getenv("DATABASE_URL");
        if (rawUrl == null || rawUrl.isBlank()) {
            rawUrl = System.getenv("DB_URL");
        }
        if (rawUrl == null || rawUrl.isBlank()) {
            rawUrl = defaultDbUrl;
        }

        // 2. Automatically handle postgres:// or postgresql:// URLs from Render/Neon/Supabase
        if (rawUrl != null && (rawUrl.startsWith("postgres://") || rawUrl.startsWith("postgresql://"))) {
            try {
                // Replace scheme temporarily to parse via java.net.URI safely
                String cleanUrl = rawUrl.replaceFirst("^postgres(ql)?://", "http://");
                URI uri = new URI(cleanUrl);

                String host = uri.getHost();
                int port = uri.getPort() == -1 ? 5432 : uri.getPort();
                String path = uri.getPath();
                String query = uri.getQuery();

                String jdbcUrl = "jdbc:postgresql://" + host + ":" + port + path;
                if (query != null && !query.isBlank()) {
                    jdbcUrl += "?" + query;
                } else {
                    jdbcUrl += "?sslmode=require";
                }

                config.setJdbcUrl(jdbcUrl);

                if (uri.getUserInfo() != null && !uri.getUserInfo().isBlank()) {
                    String[] userInfo = uri.getUserInfo().split(":", 2);
                    config.setUsername(userInfo[0]);
                    if (userInfo.length > 1) {
                        config.setPassword(userInfo[1]);
                    }
                }
            } catch (Exception e) {
                // Fallback to rawUrl if parsing fails
                config.setJdbcUrl(rawUrl);
            }
        } else {
            // Already formatted as jdbc:postgresql://
            config.setJdbcUrl(rawUrl);
        }

        // 3. Fallback to explicit env vars or properties if not set via URI
        if (config.getUsername() == null || config.getUsername().isBlank()) {
            String envUser = System.getenv("DB_USERNAME");
            config.setUsername(envUser != null && !envUser.isBlank() ? envUser : defaultUsername);
        }
        if (config.getPassword() == null || config.getPassword().isBlank()) {
            String envPass = System.getenv("DB_PASSWORD");
            config.setPassword(envPass != null && !envPass.isBlank() ? envPass : defaultPassword);
        }

        config.setDriverClassName("org.postgresql.Driver");
        return new HikariDataSource(config);
    }
}
