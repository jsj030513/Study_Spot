package com.studyspot.review;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ReviewSentimentAnalyzer {

    private static final List<String> POSITIVE_WORDS = List.of(
            "좋", "추천", "친절", "조용", "깔끔", "쾌적", "편해", "빠르", "만족", "최고", "괜찮"
    );
    private static final List<String> NEGATIVE_WORDS = List.of(
            "별로", "불친절", "시끄", "더럽", "느림", "불편", "최악", "비싸", "화남", "짜증", "나쁨", "싫"
    );

    public ReviewSentiment analyze(String text) {
        int positiveScore = countMatches(text, POSITIVE_WORDS);
        int negativeScore = countMatches(text, NEGATIVE_WORDS);
        return negativeScore > positiveScore ? ReviewSentiment.NEGATIVE : ReviewSentiment.POSITIVE;
    }

    private int countMatches(String text, List<String> words) {
        if (text == null) {
            return 0;
        }
        int score = 0;
        for (String word : words) {
            if (text.contains(word)) {
                score++;
            }
        }
        return score;
    }
}
