package com.studyspot.cafe;

import java.math.BigDecimal;

public record CafeResponse(
        String cafeId,
        String name,
        BigDecimal latitude,
        BigDecimal longitude,
        String address,
        String telNo,
        FacilityResponse facility,
        int recommendScore
) {
    public static CafeResponse from(Cafe cafe) {
        return new CafeResponse(
                cafe.cafeId(),
                cafe.name(),
                cafe.latitude(),
                cafe.longitude(),
                cafe.address(),
                cafe.telNo(),
                new FacilityResponse(cafe.outletFlag(), cafe.noiseLevel(), cafe.wifiStatus(), cafe.seatType()),
                RecommendationScore.calculate(cafe)
        );
    }
}
