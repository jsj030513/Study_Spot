package com.studyspot.user;

public record LoginResponse(
        String token,
        UserResponse user
) {
}
