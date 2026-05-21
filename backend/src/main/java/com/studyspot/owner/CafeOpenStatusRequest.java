package com.studyspot.owner;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CafeOpenStatusRequest(
        @NotNull
        Boolean open,

        @Size(max = 100)
        String message
) {
}
