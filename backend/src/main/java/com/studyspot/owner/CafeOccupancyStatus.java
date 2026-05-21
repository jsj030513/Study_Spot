package com.studyspot.owner;

import java.time.LocalDateTime;

record CafeOccupancyStatus(
        String placeId,
        int currentCount,
        int capacity,
        CongestionLevel congestionLevel,
        LocalDateTime updatedAt
) {
    CafeOccupancyStatusResponse toResponse() {
        int occupancyRate = capacity <= 0 ? 100 : Math.min(100, (int) Math.round(currentCount * 100.0 / capacity));
        return new CafeOccupancyStatusResponse(
                placeId,
                currentCount,
                capacity,
                occupancyRate,
                congestionLevel.name(),
                updatedAt
        );
    }
}
