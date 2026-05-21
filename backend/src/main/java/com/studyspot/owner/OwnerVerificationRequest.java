package com.studyspot.owner;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record OwnerVerificationRequest(
        @NotBlank
        String placeId,

        @NotBlank
        @Pattern(regexp = "^[0-9-]{10,20}$")
        String businessNumber,

        @NotBlank
        @Size(max = 500)
        String documentUrl
) {
}
