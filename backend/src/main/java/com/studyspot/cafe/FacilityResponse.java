package com.studyspot.cafe;

public record FacilityResponse(
        String outletFlag,
        String noiseLevel,
        String wifiStatus,
        String seatType
) {
}
