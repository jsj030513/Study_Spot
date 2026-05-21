package com.studyspot.review;

public enum ReviewSentiment {
    POSITIVE("positive"),
    NEGATIVE("negative");

    private final String faceType;

    ReviewSentiment(String faceType) {
        this.faceType = faceType;
    }

    public String faceType() {
        return faceType;
    }
}
