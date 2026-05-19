package com.studyspot.user;

import jakarta.validation.constraints.Size;

public record UpdateMeRequest(
        @Size(max = 30)
        String name,

        @Size(min = 8, max = 100, message = "비밀번호는 8자 이상이어야 합니다.")
        String password
) {
}
