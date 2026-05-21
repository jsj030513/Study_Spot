package com.studyspot.review;

import java.time.LocalDate;

record CafeReview(
        String reviewId,
        String placeId,
        String userId,
        String cleanedText,
        ReviewSentiment sentiment,
        boolean clean,
        LocalDate registeredDate
) {
    CafeReviewResponse toResponse() {
        return new CafeReviewResponse(
                reviewId,
                placeId,
                userId,
                cleanedText,
                sentiment.name(),
                sentiment.faceType(),
                clean,
                registeredDate
        );
    }
}
