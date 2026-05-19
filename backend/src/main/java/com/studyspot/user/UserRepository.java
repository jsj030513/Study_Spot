package com.studyspot.user;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<User> rowMapper = (rs, rowNum) -> new User(
            rs.getString("USER_ID"),
            rs.getString("USER_PW"),
            rs.getString("USER_NM"),
            rs.getString("ROLE_TY"),
            rs.getDate("REG_DT") == null ? null : rs.getDate("REG_DT").toLocalDate()
    );

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean existsById(String userId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM user_master WHERE USER_ID = ?",
                Integer.class,
                userId
        );
        return count != null && count > 0;
    }

    public long count() {
        Long count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_master", Long.class);
        return count == null ? 0 : count;
    }

    public Optional<User> findById(String userId) {
        List<User> users = jdbcTemplate.query(
                "SELECT USER_ID, USER_PW, USER_NM, ROLE_TY, REG_DT FROM user_master WHERE USER_ID = ?",
                rowMapper,
                userId
        );
        return users.stream().findFirst();
    }

    public List<User> findAll(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return jdbcTemplate.query(
                    "SELECT USER_ID, USER_PW, USER_NM, ROLE_TY, REG_DT FROM user_master ORDER BY REG_DT DESC, USER_ID",
                    rowMapper
            );
        }

        String like = "%" + keyword.trim() + "%";
        return jdbcTemplate.query(
                """
                SELECT USER_ID, USER_PW, USER_NM, ROLE_TY, REG_DT
                FROM user_master
                WHERE USER_ID LIKE ? OR USER_NM LIKE ?
                ORDER BY REG_DT DESC, USER_ID
                """,
                rowMapper,
                like,
                like
        );
    }

    public void save(String userId, String encodedPassword, String name, String role) {
        jdbcTemplate.update(
                "INSERT INTO user_master (USER_ID, USER_PW, USER_NM, ROLE_TY, REG_DT) VALUES (?, ?, ?, ?, CURDATE())",
                userId,
                encodedPassword,
                name,
                role
        );
    }

    public void updateMe(String userId, String name, String encodedPassword) {
        if (name != null && encodedPassword != null) {
            jdbcTemplate.update(
                    "UPDATE user_master SET USER_NM = ?, USER_PW = ? WHERE USER_ID = ?",
                    name,
                    encodedPassword,
                    userId
            );
            return;
        }
        if (name != null) {
            jdbcTemplate.update("UPDATE user_master SET USER_NM = ? WHERE USER_ID = ?", name, userId);
        }
        if (encodedPassword != null) {
            jdbcTemplate.update("UPDATE user_master SET USER_PW = ? WHERE USER_ID = ?", encodedPassword, userId);
        }
    }

    public void updateByAdmin(String userId, String name, String role) {
        if (name != null && role != null) {
            jdbcTemplate.update("UPDATE user_master SET USER_NM = ?, ROLE_TY = ? WHERE USER_ID = ?", name, role, userId);
            return;
        }
        if (name != null) {
            jdbcTemplate.update("UPDATE user_master SET USER_NM = ? WHERE USER_ID = ?", name, userId);
        }
        if (role != null) {
            jdbcTemplate.update("UPDATE user_master SET ROLE_TY = ? WHERE USER_ID = ?", role, userId);
        }
    }

    public void delete(String userId) {
        jdbcTemplate.update("DELETE FROM user_master WHERE USER_ID = ?", userId);
    }
}
