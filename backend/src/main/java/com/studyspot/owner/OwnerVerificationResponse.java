package com.studyspot.owner;

import java.time.LocalDateTime;

public record OwnerVerificationResponse(
        String verificationId,
        String userId,
        String placeId,
        String businessNumber,
        String documentUrl,
        String status,
        String rejectReason,
        LocalDateTime requestedAt,
        LocalDateTime reviewedAt
) {
}
