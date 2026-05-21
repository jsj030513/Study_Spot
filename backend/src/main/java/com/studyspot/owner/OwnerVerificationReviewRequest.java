package com.studyspot.owner;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record OwnerVerificationReviewRequest(
        @NotBlank
        @Pattern(regexp = "^(APPROVED|REJECTED)$")
        String status,

        @Size(max = 255)
        String rejectReason
) {
}
