package com.studyspot.owner;

import java.time.LocalDateTime;

public record CafeOccupancyStatusResponse(
        String placeId,
        int currentCount,
        int capacity,
        int occupancyRate,
        String congestionLevel,
        LocalDateTime updatedAt
) {
}
