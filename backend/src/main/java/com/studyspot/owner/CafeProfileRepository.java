package com.studyspot.owner;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CafeProfileRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<CafeProfile> rowMapper = (rs, rowNum) -> new CafeProfile(
            rs.getString("PLACE_ID"),
            rs.getString("INTRO_TXT"),
            rs.getString("NOTICE_TXT"),
            rs.getString("OPENING_HOURS"),
            rs.getString("MENU_TXT"),
            rs.getString("SNS_URL"),
            rs.getTimestamp("UPDATED_AT") == null ? null : rs.getTimestamp("UPDATED_AT").toLocalDateTime()
    );

    public CafeProfileRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<CafeProfile> findByPlaceId(String placeId) {
        List<CafeProfile> profiles = jdbcTemplate.query(
                """
                SELECT PLACE_ID, INTRO_TXT, NOTICE_TXT, OPENING_HOURS, MENU_TXT, SNS_URL, UPDATED_AT
                FROM cafe_profile
                WHERE PLACE_ID = ?
                """,
                rowMapper,
                placeId
        );
        return profiles.stream().findFirst();
    }

    public void insert(String placeId, CafeProfileRequest request) {
        jdbcTemplate.update(
                """
                INSERT INTO cafe_profile
                (PLACE_ID, INTRO_TXT, NOTICE_TXT, OPENING_HOURS, MENU_TXT, SNS_URL, UPDATED_AT)
                VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)
                """,
                placeId,
                request.introText(),
                request.noticeText(),
                request.openingHours(),
                request.menuText(),
                request.snsUrl()
        );
    }

    public void update(String placeId, CafeProfileRequest request) {
        CafeProfile current = findByPlaceId(placeId).orElseThrow();
        jdbcTemplate.update(
                """
                UPDATE cafe_profile
                SET INTRO_TXT = ?, NOTICE_TXT = ?, OPENING_HOURS = ?, MENU_TXT = ?, SNS_URL = ?,
                    UPDATED_AT = CURRENT_TIMESTAMP
                WHERE PLACE_ID = ?
                """,
                valueOrCurrent(request.introText(), current.introText()),
                valueOrCurrent(request.noticeText(), current.noticeText()),
                valueOrCurrent(request.openingHours(), current.openingHours()),
                valueOrCurrent(request.menuText(), current.menuText()),
                valueOrCurrent(request.snsUrl(), current.snsUrl()),
                placeId
        );
    }

    public void delete(String placeId) {
        jdbcTemplate.update("DELETE FROM cafe_profile WHERE PLACE_ID = ?", placeId);
    }

    private String valueOrCurrent(String value, String current) {
        return value == null ? current : value;
    }
}
