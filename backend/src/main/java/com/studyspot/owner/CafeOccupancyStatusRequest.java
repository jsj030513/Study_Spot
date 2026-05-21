package com.studyspot.owner;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public record CafeOccupancyStatusRequest(
        @Min(0)
        int currentCount,

        @Min(1)
        int capacity,

        @Pattern(regexp = "^(LOW|MEDIUM|HIGH)$")
        String congestionLevel
) {
}
