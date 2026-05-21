package com.studyspot.owner;

import java.time.LocalDateTime;

record CafePhoto(
        String photoId,
        String placeId,
        String photoUrl,
        int displayOrder,
        LocalDateTime registeredAt
) {
    CafePhotoResponse toResponse() {
        return new CafePhotoResponse(photoId, placeId, photoUrl, displayOrder, registeredAt);
    }
}
