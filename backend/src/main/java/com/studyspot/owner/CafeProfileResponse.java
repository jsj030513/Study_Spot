package com.studyspot.owner;

import java.time.LocalDateTime;

public record CafeProfileResponse(
        String placeId,
        String introText,
        String noticeText,
        String openingHours,
        String menuText,
        String snsUrl,
        LocalDateTime updatedAt
) {
}
