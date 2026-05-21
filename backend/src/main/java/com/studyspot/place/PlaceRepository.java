package com.studyspot.place;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PlaceRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Place> rowMapper = (rs, rowNum) -> new Place(
            rs.getString("PLACE_ID"),
            rs.getString("PLACE_NM"),
            PlaceType.from(rs.getString("PLACE_TY")),
            rs.getBigDecimal("LAT"),
            rs.getBigDecimal("LNT"),
            rs.getString("ADDR"),
            rs.getString("TEL_NO"),
            rs.getString("WIFI_ST"),
            rs.getString("OTL_ST"),
            rs.getString("NOI_LVL"),
            rs.getString("SEAT_TY"),
            rs.getString("DESC_TXT")
    );

    public PlaceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Place> findAll(PlaceType type, String keyword, String wifiStatus, String outletStatus,
            String noiseLevel) {
        StringBuilder sql = new StringBuilder("""
                SELECT PLACE_ID, PLACE_NM, PLACE_TY, LAT, LNT, ADDR, TEL_NO,
                       WIFI_ST, OTL_ST, NOI_LVL, SEAT_TY, DESC_TXT
                FROM place_master
                WHERE 1 = 1
                """);
        List<Object> params = new ArrayList<>();

        if (type != null) {
            sql.append(" AND PLACE_TY = ?");
            params.add(type.code());
        }
        if (keyword != null && !keyword.isBlank()) {
            sql.append(" AND (PLACE_NM LIKE ? OR ADDR LIKE ? OR DESC_TXT LIKE ?)");
            String like = "%" + keyword.trim() + "%";
            params.add(like);
            params.add(like);
            params.add(like);
        }
        if (wifiStatus != null && !wifiStatus.isBlank()) {
            sql.append(" AND WIFI_ST LIKE ?");
            params.add("%" + wifiStatus.trim() + "%");
        }
        if (outletStatus != null && !outletStatus.isBlank()) {
            sql.append(" AND OTL_ST LIKE ?");
            params.add("%" + outletStatus.trim() + "%");
        }
        if (noiseLevel != null && !noiseLevel.isBlank()) {
            sql.append(" AND NOI_LVL LIKE ?");
            params.add("%" + noiseLevel.trim() + "%");
        }

        sql.append(" ORDER BY PLACE_TY, PLACE_ID");
        return jdbcTemplate.query(sql.toString(), rowMapper, params.toArray());
    }

    public Optional<Place> findById(String placeId) {
        List<Place> places = jdbcTemplate.query(
                """
                SELECT PLACE_ID, PLACE_NM, PLACE_TY, LAT, LNT, ADDR, TEL_NO,
                       WIFI_ST, OTL_ST, NOI_LVL, SEAT_TY, DESC_TXT
                FROM place_master
                WHERE PLACE_ID = ?
                """,
                rowMapper,
                placeId
        );
        return places.stream().findFirst();
    }

    public boolean isCafe(String placeId) {
        return findById(placeId)
                .map(place -> place.type() == PlaceType.CAFE)
                .orElse(false);
    }

    public List<Place> findOwnerCafes(String ownerUserId) {
        return jdbcTemplate.query(
                """
                SELECT p.PLACE_ID, p.PLACE_NM, p.PLACE_TY, p.LAT, p.LNT, p.ADDR, p.TEL_NO,
                       p.WIFI_ST, p.OTL_ST, p.NOI_LVL, p.SEAT_TY, p.DESC_TXT
                FROM place_master p
                INNER JOIN place_owner o ON p.PLACE_ID = o.PLACE_ID
                WHERE o.USER_ID = ? AND p.PLACE_TY = 'cafe'
                ORDER BY p.PLACE_ID
                """,
                rowMapper,
                ownerUserId
        );
    }

    public boolean isOwnerOfCafe(String ownerUserId, String placeId) {
        Integer count = jdbcTemplate.queryForObject(
                """
                SELECT COUNT(*)
                FROM place_master p
                INNER JOIN place_owner o ON p.PLACE_ID = o.PLACE_ID
                WHERE o.USER_ID = ? AND p.PLACE_ID = ? AND p.PLACE_TY = 'cafe'
                """,
                Integer.class,
                ownerUserId,
                placeId
        );
        return count != null && count > 0;
    }

    public void addOwnerIfAbsent(String userId, String placeId) {
        if (isOwnerOfCafe(userId, placeId)) {
            return;
        }
        jdbcTemplate.update(
                "INSERT INTO place_owner (USER_ID, PLACE_ID) VALUES (?, ?)",
                userId,
                placeId
        );
    }

    public long count() {
        Long count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM place_master", Long.class);
        return count == null ? 0 : count;
    }

    public String nextPlaceId() {
        String maxId = jdbcTemplate.queryForObject("SELECT MAX(PLACE_ID) FROM place_master", String.class);
        if (maxId == null || !maxId.matches("^PLACE\\d{8}$")) {
            return "PLACE00000001";
        }
        int nextNumber = Integer.parseInt(maxId.substring(5)) + 1;
        return "PLACE" + String.format("%08d", nextNumber);
    }

    public void insert(String placeId, PlaceCreateRequest request) {
        jdbcTemplate.update(
                """
                INSERT INTO place_master
                (PLACE_ID, PLACE_NM, PLACE_TY, LAT, LNT, ADDR, TEL_NO, WIFI_ST, OTL_ST, NOI_LVL, SEAT_TY, DESC_TXT)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """,
                placeId,
                request.name(),
                PlaceType.from(request.type()).code(),
                request.latitude(),
                request.longitude(),
                request.address(),
                request.telNo(),
                request.wifiStatus(),
                request.outletStatus(),
                request.noiseLevel(),
                request.seatType(),
                request.description()
        );
    }

    public void update(String placeId, PlaceUpdateRequest request) {
        Place current = findById(placeId).orElseThrow();
        jdbcTemplate.update(
                """
                UPDATE place_master
                SET PLACE_NM = ?, PLACE_TY = ?, LAT = ?, LNT = ?, ADDR = ?, TEL_NO = ?,
                    WIFI_ST = ?, OTL_ST = ?, NOI_LVL = ?, SEAT_TY = ?, DESC_TXT = ?
                WHERE PLACE_ID = ?
                """,
                valueOrCurrent(request.name(), current.name()),
                request.type() == null ? current.type().code() : PlaceType.from(request.type()).code(),
                request.latitude() == null ? current.latitude() : request.latitude(),
                request.longitude() == null ? current.longitude() : request.longitude(),
                valueOrCurrent(request.address(), current.address()),
                valueOrCurrent(request.telNo(), current.telNo()),
                valueOrCurrent(request.wifiStatus(), current.wifiStatus()),
                valueOrCurrent(request.outletStatus(), current.outletStatus()),
                valueOrCurrent(request.noiseLevel(), current.noiseLevel()),
                valueOrCurrent(request.seatType(), current.seatType()),
                valueOrCurrent(request.description(), current.description()),
                placeId
        );
    }

    public void delete(String placeId) {
        jdbcTemplate.update("DELETE FROM place_master WHERE PLACE_ID = ?", placeId);
    }

    private String valueOrCurrent(String value, String current) {
        return value == null ? current : value;
    }
}
