package com.studyspot.owner;

import java.time.LocalDateTime;

record CafeProfile(
        String placeId,
        String introText,
        String noticeText,
        String openingHours,
        String menuText,
        String snsUrl,
        LocalDateTime updatedAt
) {
    CafeProfileResponse toResponse() {
        return new CafeProfileResponse(placeId, introText, noticeText, openingHours, menuText, snsUrl, updatedAt);
    }
}
