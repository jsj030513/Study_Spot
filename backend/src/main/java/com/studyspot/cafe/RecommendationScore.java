package com.studyspot.cafe;

public final class RecommendationScore {

    private RecommendationScore() {
    }

    public static int calculate(Cafe cafe) {
        int score = 50;

        if ("Y".equalsIgnoreCase(cafe.outletFlag())) {
            score += 15;
        }
        if (containsAny(cafe.wifiStatus(), "좋", "우수", "있", "암호")) {
            score += 15;
        }
        if (containsAny(cafe.noiseLevel(), "낮", "조용", "보통")) {
            score += 10;
        }
        if (containsAny(cafe.seatType(), "넓", "개인", "편")) {
            score += 10;
        }

        return Math.min(score, 100);
    }

    private static boolean containsAny(String value, String... keywords) {
        if (value == null) {
            return false;
        }
        for (String keyword : keywords) {
            if (value.contains(keyword)) {
                return true;
            }
        }
        return false;
    }
}
