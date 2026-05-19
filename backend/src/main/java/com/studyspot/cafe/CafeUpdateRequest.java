package com.studyspot.cafe;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

public record CafeUpdateRequest(
        @Size(max = 50)
        String name,

        @DecimalMin("-90.0")
        @DecimalMax("90.0")
        BigDecimal latitude,

        @DecimalMin("-180.0")
        @DecimalMax("180.0")
        BigDecimal longitude,

        @Size(max = 100)
        String address,

        @Size(max = 15)
        String telNo,

        @Size(max = 1)
        String outletFlag,

        @Size(max = 10)
        String noiseLevel,

        @Size(max = 10)
        String wifiStatus,

        @Size(max = 20)
        String seatType
) {
}
