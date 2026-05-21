package com.studyspot.owner;

import java.time.LocalDateTime;

public record CafeOpenStatusResponse(
        String placeId,
        boolean open,
        String status,
        String message,
        LocalDateTime updatedAt
) {
}
