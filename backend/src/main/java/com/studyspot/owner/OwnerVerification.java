package com.studyspot.owner;

import java.time.LocalDateTime;

record OwnerVerification(
        String verificationId,
        String userId,
        String placeId,
        String businessNumber,
        String documentUrl,
        OwnerVerificationStatus status,
        String rejectReason,
        LocalDateTime requestedAt,
        LocalDateTime reviewedAt
) {
    OwnerVerificationResponse toResponse() {
        return new OwnerVerificationResponse(
                verificationId,
                userId,
                placeId,
                businessNumber,
                documentUrl,
                status.name(),
                rejectReason,
                requestedAt,
                reviewedAt
        );
    }
}
