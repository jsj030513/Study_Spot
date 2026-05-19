package com.studyspot.user;

import java.time.LocalDate;

public record User(
        String userId,
        String password,
        String name,
        String role,
        LocalDate registeredDate
) {
}
