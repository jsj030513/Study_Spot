package com.studyspot.cafe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CafeRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Cafe> rowMapper = (rs, rowNum) -> new Cafe(
            rs.getString("CAFE_ID"),
            rs.getString("CAFE_NM"),
            rs.getBigDecimal("LAT"),
            rs.getBigDecimal("LNT"),
            rs.getString("ADDR"),
            rs.getString("TEL_NO"),
            rs.getString("OTL_FLG"),
            rs.getString("NOI_LVL"),
            rs.getString("WIFI_ST"),
            rs.getString("SEAT_TY")
    );

    public CafeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Cafe> findAll(String keyword, String wifiStatus, String outletFlag, String noiseLevel) {
        StringBuilder sql = new StringBuilder("""
                SELECT i.CAFE_ID, i.CAFE_NM, i.LAT, i.LNT, i.ADDR, i.TEL_NO,
                       f.OTL_FLG, f.NOI_LVL, f.WIFI_ST, f.SEAT_TY
                FROM cafe_info i
                LEFT JOIN cafe_facility f ON i.CAFE_ID = f.CAFE_ID
                WHERE 1 = 1
                """);
        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.isBlank()) {
            sql.append(" AND (i.CAFE_NM LIKE ? OR i.ADDR LIKE ?)");
            String like = "%" + keyword.trim() + "%";
            params.add(like);
            params.add(like);
        }
        if (wifiStatus != null && !wifiStatus.isBlank()) {
            sql.append(" AND f.WIFI_ST LIKE ?");
            params.add("%" + wifiStatus.trim() + "%");
        }
        if (outletFlag != null && !outletFlag.isBlank()) {
            sql.append(" AND f.OTL_FLG = ?");
            params.add(outletFlag.trim());
        }
        if (noiseLevel != null && !noiseLevel.isBlank()) {
            sql.append(" AND f.NOI_LVL LIKE ?");
            params.add("%" + noiseLevel.trim() + "%");
        }

        sql.append(" ORDER BY i.CAFE_ID");
        return jdbcTemplate.query(sql.toString(), rowMapper, params.toArray());
    }

    public Optional<Cafe> findById(String cafeId) {
        List<Cafe> cafes = jdbcTemplate.query(
                """
                SELECT i.CAFE_ID, i.CAFE_NM, i.LAT, i.LNT, i.ADDR, i.TEL_NO,
                       f.OTL_FLG, f.NOI_LVL, f.WIFI_ST, f.SEAT_TY
                FROM cafe_info i
                LEFT JOIN cafe_facility f ON i.CAFE_ID = f.CAFE_ID
                WHERE i.CAFE_ID = ?
                """,
                rowMapper,
                cafeId
        );
        return cafes.stream().findFirst();
    }

    public String nextCafeId() {
        String maxId = jdbcTemplate.queryForObject("SELECT MAX(CAFE_ID) FROM cafe_info", String.class);
        if (maxId == null) {
            return "CAFE00000001";
        }
        int nextNumber = Integer.parseInt(maxId.substring(4)) + 1;
        return "CAFE" + String.format("%08d", nextNumber);
    }

    public void insert(String cafeId, CafeCreateRequest request) {
        jdbcTemplate.update(
                "INSERT INTO cafe_info (CAFE_ID, CAFE_NM, LAT, LNT, ADDR, TEL_NO) VALUES (?, ?, ?, ?, ?, ?)",
                cafeId,
                request.name(),
                request.latitude(),
                request.longitude(),
                request.address(),
                request.telNo()
        );
        upsertFacility(cafeId, request.outletFlag(), request.noiseLevel(), request.wifiStatus(), request.seatType());
    }

    public void updateInfo(String cafeId, CafeUpdateRequest request) {
        Cafe current = findById(cafeId).orElseThrow();
        jdbcTemplate.update(
                "UPDATE cafe_info SET CAFE_NM = ?, LAT = ?, LNT = ?, ADDR = ?, TEL_NO = ? WHERE CAFE_ID = ?",
                valueOrCurrent(request.name(), current.name()),
                request.latitude() == null ? current.latitude() : request.latitude(),
                request.longitude() == null ? current.longitude() : request.longitude(),
                valueOrCurrent(request.address(), current.address()),
                valueOrCurrent(request.telNo(), current.telNo()),
                cafeId
        );

        upsertFacility(
                cafeId,
                valueOrCurrent(request.outletFlag(), current.outletFlag()),
                valueOrCurrent(request.noiseLevel(), current.noiseLevel()),
                valueOrCurrent(request.wifiStatus(), current.wifiStatus()),
                valueOrCurrent(request.seatType(), current.seatType())
        );
    }

    public void delete(String cafeId) {
        jdbcTemplate.update("DELETE FROM cafe_facility WHERE CAFE_ID = ?", cafeId);
        jdbcTemplate.update("DELETE FROM cafe_info WHERE CAFE_ID = ?", cafeId);
    }

    private void upsertFacility(String cafeId, String outletFlag, String noiseLevel, String wifiStatus, String seatType) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM cafe_facility WHERE CAFE_ID = ?",
                Integer.class,
                cafeId
        );

        if (count != null && count > 0) {
            jdbcTemplate.update(
                    "UPDATE cafe_facility SET OTL_FLG = ?, NOI_LVL = ?, WIFI_ST = ?, SEAT_TY = ? WHERE CAFE_ID = ?",
                    outletFlag,
                    noiseLevel,
                    wifiStatus,
                    seatType,
                    cafeId
            );
            return;
        }

        jdbcTemplate.update(
                "INSERT INTO cafe_facility (CAFE_ID, OTL_FLG, NOI_LVL, WIFI_ST, SEAT_TY) VALUES (?, ?, ?, ?, ?)",
                cafeId,
                outletFlag,
                noiseLevel,
                wifiStatus,
                seatType
        );
    }

    private String valueOrCurrent(String value, String current) {
        return value == null ? current : value;
    }
}
