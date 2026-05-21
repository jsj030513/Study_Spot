package com.studyspot.owner;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CafePhotoCreateRequest(
        @NotBlank
        @Size(max = 500)
        String photoUrl,

        @Min(1)
        @Max(6)
        int displayOrder
) {
}
