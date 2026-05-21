package com.studyspot.place;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class PlaceRecommendationScoreTest {

    @Test
    void calculatesCafeScoreFromFacilityFields() {
        Place place = new Place("PLACE00000001", "추천 카페", PlaceType.CAFE, BigDecimal.ONE, BigDecimal.ONE,
                null, null, "와이파이 좋음", "많음", "조용함", "개인 콘센트 좌석", null);

        assertThat(PlaceRecommendationScore.calculate(place)).isEqualTo(100);
    }

    @Test
    void usesDifferentBaseScoresByPlaceType() {
        Place library = new Place("PLACE00000002", "도서관", PlaceType.LIBRARY, BigDecimal.ONE, BigDecimal.ONE,
                null, null, null, null, null, null, null);
        Place store = new Place("PLACE00000003", "편의점", PlaceType.STORE, BigDecimal.ONE, BigDecimal.ONE,
                null, null, null, null, null, null, null);

        assertThat(PlaceRecommendationScore.calculate(library)).isGreaterThan(PlaceRecommendationScore.calculate(store));
    }
}
