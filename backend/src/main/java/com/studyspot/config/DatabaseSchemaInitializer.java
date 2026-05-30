package com.studyspot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSchemaInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DatabaseSchemaInitializer.class);

    private final JdbcTemplate jdbcTemplate;

    public DatabaseSchemaInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        ensureOwnerVerificationSupportsNewCafeRequests();
        normalizePlaceTypes();
        seedCafePlacesFromCafeInfo();
        seedDefaultNonCafePlaces();
    }

    private void ensureOwnerVerificationSupportsNewCafeRequests() {
        if (!tableExists("owner_verification")) {
            return;
        }

        try {
            jdbcTemplate.execute("ALTER TABLE owner_verification MODIFY PLACE_ID varchar(20) DEFAULT NULL");
            if (!columnExists("owner_verification", "REQUESTED_PLACE_NM")) {
                jdbcTemplate.execute("""
                        ALTER TABLE owner_verification
                        ADD COLUMN REQUESTED_PLACE_NM varchar(80) DEFAULT NULL AFTER PLACE_ID
                        """);
            }
        } catch (Exception exception) {
            log.warn("owner_verification schema migration was skipped: {}", exception.getMessage());
        }
    }

    private boolean tableExists(String tableName) {
        Integer count = jdbcTemplate.queryForObject(
                """
                SELECT COUNT(*)
                FROM information_schema.TABLES
                WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ?
                """,
                Integer.class,
                tableName
        );
        return count != null && count > 0;
    }

    private boolean columnExists(String tableName, String columnName) {
        Integer count = jdbcTemplate.queryForObject(
                """
                SELECT COUNT(*)
                FROM information_schema.COLUMNS
                WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ? AND COLUMN_NAME = ?
                """,
                Integer.class,
                tableName,
                columnName
        );
        return count != null && count > 0;
    }

    private void normalizePlaceTypes() {
        if (!tableExists("place_master")) {
            return;
        }

        try {
            jdbcTemplate.update("UPDATE place_master SET PLACE_TY = 'store' WHERE PLACE_TY = 'convenience'");
            jdbcTemplate.update("UPDATE place_master SET PLACE_TY = 'print' WHERE PLACE_TY = 'print_shop'");
        } catch (Exception exception) {
            log.warn("place_master type normalization was skipped: {}", exception.getMessage());
        }
    }

    private void seedDefaultNonCafePlaces() {
        if (!tableExists("place_master")) {
            return;
        }

        try {
            seedPlace("LIB000000001", "백석대학교 학술정보관", "library", "36.8377770", "127.1839946",
                    "충남 천안시 동남구 백석대학로 1-12");
            seedPlace("LIB000000002", "백석대학교 본부동 도서관", "library", "36.8391882", "127.1858767",
                    "충남 천안시 동남구 백석대학로 1-11");
            seedPlace("LIB000000003", "백석문화대학 도서관", "library", "36.8382586", "127.1829669",
                    "충남 천안시 동남구 백석대학로 1-2");

            seedPlace("CON000000001", "CU 천안백석대정문점", "store", "36.8414198", "127.1817066",
                    "충남 천안시 동남구 문암로 78 1층");
            seedPlace("CON000000002", "세븐일레븐 천안백석학생복지동점", "store", "36.8406689", "127.1825567",
                    "충남 천안시 동남구 백석대학로 1-9 2층 211호");
            seedPlace("CON000000003", "CU 천안백석대진리관점", "store", "36.8401131", "127.1845193",
                    "충남 천안시 동남구 백석대학로 1-1 지하1층 101호");
            seedPlace("CON000000004", "이마트24 은혜관점", "store", "36.8386476", "127.1819442",
                    "충남 천안시 동남구 백석대학로 1-7 은혜관 101호");
            seedPlace("CON000000005", "GS25 자유관점", "store", "36.8384998", "127.1832242",
                    "충남 천안시 동남구 백석대학로 1-2");
            seedPlace("CON000000006", "CU 천안백석대지혜관점", "store", "36.8385655", "127.1843555",
                    "충남 천안시 동남구 백석대학로 1-1 지혜관동 3층 309호");
            seedPlace("CON000000007", "CU 천안백석대본부동점", "store", "36.8391803", "127.1858902",
                    "충남 천안시 동남구 백석대학로 1-11 백석대학교 본부동");
            seedPlace("CON000000008", "CU 천안백석대조형관점", "store", "36.8409088", "127.1884576",
                    "충남 천안시 동남구 백석대학로 1-18 1층 104,105,107호");
            seedPlace("CON000000009", "세븐일레븐 천안백석대학로점", "store", "36.8420231", "127.1868946",
                    "충남 천안시 동남구 문암5길 25 1층");
            seedPlace("CON000000010", "GS25 백석생활관점", "store", "36.8425553", "127.1851498",
                    "충남 천안시 동남구 백석대학로 1-19 백석생활관 2층 202호");
            seedPlace("CON000000011", "세븐일레븐 백석문암점", "store", "36.8421389", "127.1825057",
                    "충남 천안시 동남구 문암로 90 1층");
            seedPlace("CON000000012", "GS25 백석대점", "store", "36.8413307", "127.1808794",
                    "충남 천안시 동남구 문암4길 10-18 창이빌딩 1층");
            seedPlace("CON000000013", "GS25 백석대타운점", "store", "36.8411190", "127.1800456",
                    "충남 천안시 동남구 문암4길 7 1층");

            seedPlace("STA000000001", "스타오피스", "stationery", "36.8420351", "127.1817952",
                    "충남 천안시 동남구 문암로 83");
            seedPlace("STA000000002", "천안문화사", "stationery", "36.8408046", "127.1804584",
                    "충남 천안시 동남구 문암로 65");

            seedPlace("PRT000000001", "백석생활관 프린터", "print", "36.8426359", "127.1851273",
                    "충남 천안시 동남구 백석대학로 1-19 백석생활관");
            seedPlace("PRT000000002", "본부동 3층 프린터", "print", "36.8392080", "127.1859161",
                    "충남 천안시 동남구 백석대학로 1-11 3층");
            seedPlace("PRT000000003", "본부동 6층 프린터", "print", "36.8392080", "127.1859161",
                    "충남 천안시 동남구 백석대학로 1-11 6층");
            seedPlace("PRT000000004", "지혜관 3층 프린터", "print", "36.8386523", "127.1842850",
                    "충남 천안시 동남구 백석대학로 1-8 3층");
            seedPlace("PRT000000005", "목양관 지하1층 프린터", "print", "36.8408505", "127.1835535",
                    "충남 천안시 동남구 백석대학로 1-6 지하1층");
            seedPlace("PRT000000006", "학생복지관 지하1층 프린터", "print", "36.8405633", "127.1824671",
                    "충남 천안시 동남구 백석대학로 1-9 지하1층");
        } catch (Exception exception) {
            log.warn("default non-cafe place seeding was skipped: {}", exception.getMessage());
        }
    }

    private void seedCafePlacesFromCafeInfo() {
        if (!tableExists("place_master") || !tableExists("cafe_info")) {
            return;
        }

        try {
            jdbcTemplate.update("""
                    INSERT INTO place_master
                    (PLACE_ID, PLACE_NM, PLACE_TY, LAT, LNT, ADDR, TEL_NO, WIFI_ST, OTL_ST, NOI_LVL, SEAT_TY, DESC_TXT)
                    SELECT
                        i.CAFE_ID,
                        i.CAFE_NM,
                        'cafe',
                        i.LAT,
                        i.LNT,
                        i.ADDR,
                        i.TEL_NO,
                        COALESCE(f.WIFI_ST, '보통'),
                        CASE
                            WHEN f.OTL_FLG = 'Y' THEN '있음'
                            WHEN f.OTL_FLG = 'N' THEN '없음'
                            ELSE COALESCE(f.OTL_FLG, '미등록')
                        END,
                        COALESCE(f.NOI_LVL, '보통'),
                        COALESCE(f.SEAT_TY, '일반 좌석'),
                        CONCAT(i.CAFE_NM, ' 카페 정보입니다.')
                    FROM cafe_info i
                    LEFT JOIN cafe_facility f ON i.CAFE_ID = f.CAFE_ID
                    ON DUPLICATE KEY UPDATE
                        PLACE_NM = VALUES(PLACE_NM),
                        PLACE_TY = 'cafe',
                        LAT = VALUES(LAT),
                        LNT = VALUES(LNT),
                        ADDR = VALUES(ADDR),
                        TEL_NO = VALUES(TEL_NO),
                        WIFI_ST = VALUES(WIFI_ST),
                        OTL_ST = VALUES(OTL_ST),
                        NOI_LVL = VALUES(NOI_LVL),
                        SEAT_TY = VALUES(SEAT_TY),
                        DESC_TXT = VALUES(DESC_TXT)
                    """);
        } catch (Exception exception) {
            log.warn("cafe place seeding was skipped: {}", exception.getMessage());
        }
    }

    private void seedPlace(String placeId, String name, String type, String latitude, String longitude, String address) {
        jdbcTemplate.update(
                """
                INSERT INTO place_master
                (PLACE_ID, PLACE_NM, PLACE_TY, LAT, LNT, ADDR, WIFI_ST, OTL_ST, NOI_LVL, SEAT_TY, DESC_TXT)
                VALUES (?, ?, ?, ?, ?, ?, '보통', '보통', '보통', '일반 좌석', ?)
                ON DUPLICATE KEY UPDATE
                    PLACE_NM = VALUES(PLACE_NM),
                    PLACE_TY = VALUES(PLACE_TY),
                    LAT = VALUES(LAT),
                    LNT = VALUES(LNT),
                    ADDR = VALUES(ADDR)
                """,
                placeId,
                name,
                type,
                latitude,
                longitude,
                address,
                name + " 정보입니다."
        );
    }
}
