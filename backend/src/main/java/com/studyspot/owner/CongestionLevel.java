package com.studyspot.owner;

import java.util.Arrays;

import org.springframework.http.HttpStatus;

import com.studyspot.common.ApiException;

public enum CongestionLevel {
    LOW,
    MEDIUM,
    HIGH;

    public static CongestionLevel from(String value) {
        return Arrays.stream(values())
                .filter(level -> level.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "혼잡도는 LOW, MEDIUM, HIGH만 가능합니다."));
    }

    public static CongestionLevel calculate(int currentCount, int capacity) {
        if (capacity <= 0) {
            return HIGH;
        }
        double ratio = (double) currentCount / capacity;
        if (ratio < 0.4) {
            return LOW;
        }
        if (ratio < 0.75) {
            return MEDIUM;
        }
        return HIGH;
    }
}
