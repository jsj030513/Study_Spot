package com.studyspot.owner;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class OwnerVerificationRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<OwnerVerification> rowMapper = (rs, rowNum) -> new OwnerVerification(
            rs.getString("VERIFICATION_ID"),
            rs.getString("USER_ID"),
            rs.getString("PLACE_ID"),
            rs.getString("BUSINESS_NO"),
            rs.getString("DOCUMENT_URL"),
            OwnerVerificationStatus.from(rs.getString("STATUS_TY")),
            rs.getString("REJECT_REASON"),
            rs.getTimestamp("REQ_DT") == null ? null : rs.getTimestamp("REQ_DT").toLocalDateTime(),
            rs.getTimestamp("REVIEW_DT") == null ? null : rs.getTimestamp("REVIEW_DT").toLocalDateTime()
    );

    public OwnerVerificationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<OwnerVerification> findAll(OwnerVerificationStatus status) {
        if (status == null) {
            return jdbcTemplate.query(
                    """
                    SELECT VERIFICATION_ID, USER_ID, PLACE_ID, BUSINESS_NO, DOCUMENT_URL,
                           STATUS_TY, REJECT_REASON, REQ_DT, REVIEW_DT
                    FROM owner_verification
                    ORDER BY REQ_DT DESC, VERIFICATION_ID DESC
                    """,
                    rowMapper
            );
        }

        return jdbcTemplate.query(
                """
                SELECT VERIFICATION_ID, USER_ID, PLACE_ID, BUSINESS_NO, DOCUMENT_URL,
                       STATUS_TY, REJECT_REASON, REQ_DT, REVIEW_DT
                FROM owner_verification
                WHERE STATUS_TY = ?
                ORDER BY REQ_DT DESC, VERIFICATION_ID DESC
                """,
                rowMapper,
                status.name()
        );
    }

    public List<OwnerVerification> findByUserId(String userId) {
        return jdbcTemplate.query(
                """
                SELECT VERIFICATION_ID, USER_ID, PLACE_ID, BUSINESS_NO, DOCUMENT_URL,
                       STATUS_TY, REJECT_REASON, REQ_DT, REVIEW_DT
                FROM owner_verification
                WHERE USER_ID = ?
                ORDER BY REQ_DT DESC, VERIFICATION_ID DESC
                """,
                rowMapper,
                userId
        );
    }

    public Optional<OwnerVerification> findById(String verificationId) {
        List<OwnerVerification> verifications = jdbcTemplate.query(
                """
                SELECT VERIFICATION_ID, USER_ID, PLACE_ID, BUSINESS_NO, DOCUMENT_URL,
                       STATUS_TY, REJECT_REASON, REQ_DT, REVIEW_DT
                FROM owner_verification
                WHERE VERIFICATION_ID = ?
                """,
                rowMapper,
                verificationId
        );
        return verifications.stream().findFirst();
    }

    public boolean hasPending(String userId, String placeId) {
        Integer count = jdbcTemplate.queryForObject(
                """
                SELECT COUNT(*)
                FROM owner_verification
                WHERE USER_ID = ? AND PLACE_ID = ? AND STATUS_TY = 'PENDING'
                """,
                Integer.class,
                userId,
                placeId
        );
        return count != null && count > 0;
    }

    public String nextVerificationId() {
        String maxId = jdbcTemplate.queryForObject("SELECT MAX(VERIFICATION_ID) FROM owner_verification", String.class);
        if (maxId == null || !maxId.matches("^OV\\d{10}$")) {
            return "OV0000000001";
        }
        int nextNumber = Integer.parseInt(maxId.substring(2)) + 1;
        return "OV" + String.format("%010d", nextNumber);
    }

    public void insert(String verificationId, String userId, OwnerVerificationRequest request) {
        jdbcTemplate.update(
                """
                INSERT INTO owner_verification
                (VERIFICATION_ID, USER_ID, PLACE_ID, BUSINESS_NO, DOCUMENT_URL, STATUS_TY, REQ_DT)
                VALUES (?, ?, ?, ?, ?, 'PENDING', CURRENT_TIMESTAMP)
                """,
                verificationId,
                userId,
                request.placeId(),
                request.businessNumber(),
                request.documentUrl()
        );
    }

    public void review(String verificationId, OwnerVerificationStatus status, String rejectReason) {
        jdbcTemplate.update(
                """
                UPDATE owner_verification
                SET STATUS_TY = ?, REJECT_REASON = ?, REVIEW_DT = CURRENT_TIMESTAMP
                WHERE VERIFICATION_ID = ?
                """,
                status.name(),
                rejectReason,
                verificationId
        );
    }
}
