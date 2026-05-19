package com.studyspot.auth;

public record AuthUser(String userId, String name, String role) {

    public boolean isAdmin() {
        return "A".equals(role);
    }
}
