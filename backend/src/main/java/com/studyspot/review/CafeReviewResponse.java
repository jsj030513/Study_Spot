package com.studyspot.review;

import java.time.LocalDate;

public record CafeReviewResponse(
        String reviewId,
        String placeId,
        String userId,
        String content,
        String sentiment,
        String faceType,
        boolean clean,
        LocalDate registeredDate
) {
}
