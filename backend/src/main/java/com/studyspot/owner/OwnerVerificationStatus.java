package com.studyspot.owner;

import java.util.Arrays;

import org.springframework.http.HttpStatus;

import com.studyspot.common.ApiException;

public enum OwnerVerificationStatus {
    PENDING,
    APPROVED,
    REJECTED;

    public static OwnerVerificationStatus from(String value) {
        return Arrays.stream(values())
                .filter(status -> status.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "인증 상태는 PENDING, APPROVED, REJECTED만 가능합니다."));
    }
}
