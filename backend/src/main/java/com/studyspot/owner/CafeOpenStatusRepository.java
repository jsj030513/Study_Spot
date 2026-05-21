package com.studyspot.owner;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CafeOpenStatusRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<CafeOpenStatus> rowMapper = (rs, rowNum) -> new CafeOpenStatus(
            rs.getString("PLACE_ID"),
            "Y".equalsIgnoreCase(rs.getString("OPEN_FLG")),
            rs.getString("STATUS_MSG"),
            rs.getTimestamp("UPDATED_AT") == null ? null : rs.getTimestamp("UPDATED_AT").toLocalDateTime()
    );

    public CafeOpenStatusRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<CafeOpenStatus> findByPlaceId(String placeId) {
        List<CafeOpenStatus> statuses = jdbcTemplate.query(
                """
                SELECT PLACE_ID, OPEN_FLG, STATUS_MSG, UPDATED_AT
                FROM cafe_open_status
                WHERE PLACE_ID = ?
                """,
                rowMapper,
                placeId
        );
        return statuses.stream().findFirst();
    }

    public void upsert(String placeId, CafeOpenStatusRequest request) {
        if (findByPlaceId(placeId).isPresent()) {
            jdbcTemplate.update(
                    """
                    UPDATE cafe_open_status
                    SET OPEN_FLG = ?, STATUS_MSG = ?, UPDATED_AT = CURRENT_TIMESTAMP
                    WHERE PLACE_ID = ?
                    """,
                    request.open() ? "Y" : "N",
                    request.message(),
                    placeId
            );
            return;
        }

        jdbcTemplate.update(
                """
                INSERT INTO cafe_open_status (PLACE_ID, OPEN_FLG, STATUS_MSG, UPDATED_AT)
                VALUES (?, ?, ?, CURRENT_TIMESTAMP)
                """,
                placeId,
                request.open() ? "Y" : "N",
                request.message()
        );
    }
}
