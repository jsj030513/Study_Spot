package com.studyspot.place;

import java.util.Arrays;

import org.springframework.http.HttpStatus;

import com.studyspot.common.ApiException;

public enum PlaceType {
    CAFE("cafe", "카페"),
    LIBRARY("library", "도서관"),
    STORE("store", "편의점"),
    STATIONERY("stationery", "문구점"),
    PRINT("print", "프린트");

    private final String code;
    private final String label;

    PlaceType(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String code() {
        return code;
    }

    public String label() {
        return label;
    }

    public static PlaceType from(String code) {
        if ("convenience".equalsIgnoreCase(code)) {
            return STORE;
        }
        if ("print_shop".equalsIgnoreCase(code)) {
            return PRINT;
        }
        return Arrays.stream(values())
                .filter(type -> type.code.equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "지원하지 않는 장소 유형입니다."));
    }
}
