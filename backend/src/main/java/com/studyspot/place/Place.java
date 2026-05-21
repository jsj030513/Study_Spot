package com.studyspot.place;

import java.math.BigDecimal;

record Place(
        String placeId,
        String name,
        PlaceType type,
        BigDecimal latitude,
        BigDecimal longitude,
        String address,
        String telNo,
        String wifiStatus,
        String outletStatus,
        String noiseLevel,
        String seatType,
        String description
) {
    PlaceResponse toResponse() {
        return new PlaceResponse(
                placeId,
                name,
                type.code(),
                type.label(),
                latitude,
                longitude,
                address,
                telNo,
                wifiStatus,
                outletStatus,
                noiseLevel,
                seatType,
                description,
                PlaceRecommendationScore.calculate(this)
        );
    }
}
