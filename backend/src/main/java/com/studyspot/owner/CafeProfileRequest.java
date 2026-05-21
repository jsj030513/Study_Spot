package com.studyspot.owner;

import jakarta.validation.constraints.Size;

public record CafeProfileRequest(
        @Size(max = 1000)
        String introText,

        @Size(max = 1000)
        String noticeText,

        @Size(max = 255)
        String openingHours,

        @Size(max = 1000)
        String menuText,

        @Size(max = 255)
        String snsUrl
) {
}
