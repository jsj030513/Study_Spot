package com.studyspot.admin;

public record AdminSummaryResponse(
        long userCount,
        long placeCount
) {
}
