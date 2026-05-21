package com.studyspot.place;

import java.math.BigDecimal;

public record PlaceResponse(
        String placeId,
        String name,
        String type,
        String typeName,
        BigDecimal latitude,
        BigDecimal longitude,
        String address,
        String telNo,
        String wifiStatus,
        String outletStatus,
        String noiseLevel,
        String seatType,
        String description,
        int recommendScore
) {
    static PlaceResponse from(Place place) {
        return place.toResponse();
    }
}
