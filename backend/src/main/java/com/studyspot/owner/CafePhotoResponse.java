package com.studyspot.owner;

import java.time.LocalDateTime;

public record CafePhotoResponse(
        String photoId,
        String placeId,
        String photoUrl,
        int displayOrder,
        LocalDateTime registeredAt
) {
}
