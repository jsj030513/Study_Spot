package com.studyspot.cafe;

import java.math.BigDecimal;

public record Cafe(
        String cafeId,
        String name,
        BigDecimal latitude,
        BigDecimal longitude,
        String address,
        String telNo,
        String outletFlag,
        String noiseLevel,
        String wifiStatus,
        String seatType
) {
}
