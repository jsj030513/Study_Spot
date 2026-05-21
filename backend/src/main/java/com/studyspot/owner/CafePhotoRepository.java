package com.studyspot.owner;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CafePhotoRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<CafePhoto> rowMapper = (rs, rowNum) -> new CafePhoto(
            rs.getString("PHOTO_ID"),
            rs.getString("PLACE_ID"),
            rs.getString("PHOTO_URL"),
            rs.getInt("DISPLAY_ORD"),
            rs.getTimestamp("REG_DT") == null ? null : rs.getTimestamp("REG_DT").toLocalDateTime()
    );

    public CafePhotoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CafePhoto> findByPlaceId(String placeId) {
        return jdbcTemplate.query(
                """
                SELECT PHOTO_ID, PLACE_ID, PHOTO_URL, DISPLAY_ORD, REG_DT
                FROM cafe_photo
                WHERE PLACE_ID = ?
                ORDER BY DISPLAY_ORD, PHOTO_ID
                """,
                rowMapper,
                placeId
        );
    }

    public Optional<CafePhoto> findById(String photoId) {
        List<CafePhoto> photos = jdbcTemplate.query(
                """
                SELECT PHOTO_ID, PLACE_ID, PHOTO_URL, DISPLAY_ORD, REG_DT
                FROM cafe_photo
                WHERE PHOTO_ID = ?
                """,
                rowMapper,
                photoId
        );
        return photos.stream().findFirst();
    }

    public int countByPlaceId(String placeId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM cafe_photo WHERE PLACE_ID = ?",
                Integer.class,
                placeId
        );
        return count == null ? 0 : count;
    }

    public String nextPhotoId() {
        String maxId = jdbcTemplate.queryForObject("SELECT MAX(PHOTO_ID) FROM cafe_photo", String.class);
        if (maxId == null || !maxId.matches("^PHOTO\\d{7}$")) {
            return "PHOTO0000001";
        }
        int nextNumber = Integer.parseInt(maxId.substring(5)) + 1;
        return "PHOTO" + String.format("%07d", nextNumber);
    }

    public void insert(String photoId, String placeId, CafePhotoCreateRequest request) {
        jdbcTemplate.update(
                """
                INSERT INTO cafe_photo (PHOTO_ID, PLACE_ID, PHOTO_URL, DISPLAY_ORD, REG_DT)
                VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)
                """,
                photoId,
                placeId,
                request.photoUrl(),
                request.displayOrder()
        );
    }

    public void update(String photoId, CafePhotoUpdateRequest request) {
        CafePhoto current = findById(photoId).orElseThrow();
        jdbcTemplate.update(
                """
                UPDATE cafe_photo
                SET PHOTO_URL = ?, DISPLAY_ORD = ?
                WHERE PHOTO_ID = ?
                """,
                request.photoUrl() == null ? current.photoUrl() : request.photoUrl(),
                request.displayOrder() == null ? current.displayOrder() : request.displayOrder(),
                photoId
        );
    }

    public void delete(String photoId) {
        jdbcTemplate.update("DELETE FROM cafe_photo WHERE PHOTO_ID = ?", photoId);
    }
}
