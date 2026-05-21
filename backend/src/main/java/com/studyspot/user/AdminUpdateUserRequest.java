package com.studyspot.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AdminUpdateUserRequest(
        @Size(max = 30)
        String name,

        @Pattern(regexp = "^[AUO]$", message = "권한은 A, U, O만 가능합니다.")
        String role
) {
}
