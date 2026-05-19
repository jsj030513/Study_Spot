package com.studyspot.user;

import java.time.LocalDate;

public record UserResponse(
        String userId,
        String name,
        String role,
        LocalDate registeredDate
) {
    public static UserResponse from(User user) {
        return new UserResponse(user.userId(), user.name(), user.role(), user.registeredDate());
    }
}
