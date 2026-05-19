package com.studyspot.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AdminUpdateUserRequest(
        @Size(max = 30)
        String name,

        @Pattern(regexp = "^[AU]$", message = "권한은 A 또는 U만 가능합니다.")
        String role
) {
}
