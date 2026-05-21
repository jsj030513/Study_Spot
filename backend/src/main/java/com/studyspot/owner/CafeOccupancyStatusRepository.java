package com.studyspot.owner;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CafeOccupancyStatusRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<CafeOccupancyStatus> rowMapper = (rs, rowNum) -> new CafeOccupancyStatus(
            rs.getString("PLACE_ID"),
            rs.getInt("CURRENT_CNT"),
            rs.getInt("CAPACITY_CNT"),
            CongestionLevel.from(rs.getString("CONGESTION_TY")),
            rs.getTimestamp("UPDATED_AT") == null ? null : rs.getTimestamp("UPDATED_AT").toLocalDateTime()
    );

    public CafeOccupancyStatusRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<CafeOccupancyStatus> findByPlaceId(String placeId) {
        List<CafeOccupancyStatus> statuses = jdbcTemplate.query(
                """
                SELECT PLACE_ID, CURRENT_CNT, CAPACITY_CNT, CONGESTION_TY, UPDATED_AT
                FROM cafe_occupancy_status
                WHERE PLACE_ID = ?
                """,
                rowMapper,
                placeId
        );
        return statuses.stream().findFirst();
    }

    public void upsert(String placeId, int currentCount, int capacity, CongestionLevel congestionLevel) {
        if (findByPlaceId(placeId).isPresent()) {
            jdbcTemplate.update(
                    """
                    UPDATE cafe_occupancy_status
                    SET CURRENT_CNT = ?, CAPACITY_CNT = ?, CONGESTION_TY = ?, UPDATED_AT = CURRENT_TIMESTAMP
                    WHERE PLACE_ID = ?
                    """,
                    currentCount,
                    capacity,
                    congestionLevel.name(),
                    placeId
            );
            return;
        }

        jdbcTemplate.update(
                """
                INSERT INTO cafe_occupancy_status
                (PLACE_ID, CURRENT_CNT, CAPACITY_CNT, CONGESTION_TY, UPDATED_AT)
                VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)
                """,
                placeId,
                currentCount,
                capacity,
                congestionLevel.name()
        );
    }
}
