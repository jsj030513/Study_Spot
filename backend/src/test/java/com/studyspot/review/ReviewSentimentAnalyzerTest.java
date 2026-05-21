package com.studyspot.review;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ReviewSentimentAnalyzerTest {

    private final ReviewSentimentAnalyzer analyzer = new ReviewSentimentAnalyzer();

    @Test
    void detectsPositiveReview() {
        assertThat(analyzer.analyze("조용하고 콘센트도 많아서 좋아요")).isEqualTo(ReviewSentiment.POSITIVE);
    }

    @Test
    void detectsNegativeReview() {
        assertThat(analyzer.analyze("시끄럽고 불편해서 별로예요")).isEqualTo(ReviewSentiment.NEGATIVE);
    }
}
