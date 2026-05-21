package com.studyspot.review;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CafeReviewCreateRequest(
        @NotBlank
        @Size(max = 100)
        String content
) {
}
