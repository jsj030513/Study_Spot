package com.studyspot.review;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CafeReviewRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<CafeReview> rowMapper = (rs, rowNum) -> new CafeReview(
            rs.getString("REVIEW_ID"),
            rs.getString("PLACE_ID"),
            rs.getString("USER_ID"),
            rs.getString("CLEAN_TXT"),
            ReviewSentiment.valueOf(rs.getString("SENTIMENT_TY")),
            "Y".equalsIgnoreCase(rs.getString("CLEAN_FLG")),
            rs.getDate("REG_DT") == null ? null : rs.getDate("REG_DT").toLocalDate()
    );

    public CafeReviewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CafeReview> findByPlaceId(String placeId) {
        return jdbcTemplate.query(
                """
                SELECT REVIEW_ID, PLACE_ID, USER_ID, CLEAN_TXT, SENTIMENT_TY, CLEAN_FLG, REG_DT
                FROM cafe_review
                WHERE PLACE_ID = ?
                ORDER BY REG_DT DESC, REVIEW_ID DESC
                """,
                rowMapper,
                placeId
        );
    }

    public String nextReviewId() {
        String maxId = jdbcTemplate.queryForObject("SELECT MAX(REVIEW_ID) FROM cafe_review", String.class);
        if (maxId == null || !maxId.matches("^REV\\d{9}$")) {
            return "REV000000001";
        }
        int nextNumber = Integer.parseInt(maxId.substring(3)) + 1;
        return "REV" + String.format("%09d", nextNumber);
    }

    public void insert(String reviewId, String placeId, String userId, String originalText, CleanBotResult cleanResult,
            ReviewSentiment sentiment) {
        jdbcTemplate.update(
                """
                INSERT INTO cafe_review
                (REVIEW_ID, PLACE_ID, USER_ID, REVIEW_TXT, CLEAN_TXT, SENTIMENT_TY, CLEAN_FLG, REG_DT)
                VALUES (?, ?, ?, ?, ?, ?, ?, CURDATE())
                """,
                reviewId,
                placeId,
                userId,
                originalText,
                cleanResult.cleanedText(),
                sentiment.name(),
                cleanResult.clean() ? "Y" : "N"
        );
    }
}
