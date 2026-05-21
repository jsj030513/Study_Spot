package com.studyspot.user;

public record UserIdAvailabilityResponse(
        String userId,
        boolean available
) {
}
