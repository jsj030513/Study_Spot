package com.studyspot.place;

final class PlaceRecommendationScore {

    private PlaceRecommendationScore() {
    }

    static int calculate(Place place) {
        int score = switch (place.type()) {
            case CAFE -> 50;
            case LIBRARY -> 65;
            case STORE -> 40;
            case STATIONERY -> 45;
            case PRINT -> 45;
        };

        if (containsAny(place.wifiStatus(), "좋", "우수", "빠", "강", "양호")) {
            score += 15;
        }
        if (containsAny(place.outletStatus(), "Y", "있", "많", "보통")) {
            score += 15;
        }
        if (containsAny(place.noiseLevel(), "조용", "보통", "낮")) {
            score += 10;
        }
        if (containsAny(place.seatType(), "개인", "넓", "콘센트", "스터디", "열람", "편")) {
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
