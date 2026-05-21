package com.studyspot.owner;

import java.time.LocalDateTime;

record CafeOpenStatus(
        String placeId,
        boolean open,
        String message,
        LocalDateTime updatedAt
) {
    CafeOpenStatusResponse toResponse() {
        return new CafeOpenStatusResponse(placeId, open, open ? "OPEN" : "CLOSED", message, updatedAt);
    }
}
