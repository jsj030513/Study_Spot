package com.studyspot.review;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studyspot.common.ApiException;
import com.studyspot.place.PlaceService;

@Service
public class CafeReviewService {

    private final CafeReviewRepository cafeReviewRepository;
    private final PlaceService placeService;
    private final CleanBot cleanBot;
    private final ReviewSentimentAnalyzer sentimentAnalyzer;

    public CafeReviewService(CafeReviewRepository cafeReviewRepository, PlaceService placeService, CleanBot cleanBot,
            ReviewSentimentAnalyzer sentimentAnalyzer) {
        this.cafeReviewRepository = cafeReviewRepository;
        this.placeService = placeService;
        this.cleanBot = cleanBot;
        this.sentimentAnalyzer = sentimentAnalyzer;
    }

    public List<CafeReviewResponse> findCafeReviews(String placeId) {
        assertCafe(placeId);
        return cafeReviewRepository.findByPlaceId(placeId).stream()
                .map(CafeReview::toResponse)
                .toList();
    }

    @Transactional
    public CafeReviewResponse createCafeReview(String placeId, String userId, CafeReviewCreateRequest request) {
        assertCafe(placeId);
        CleanBotResult cleanResult = cleanBot.clean(request.content());
        ReviewSentiment sentiment = cleanResult.clean()
                ? sentimentAnalyzer.analyze(cleanResult.cleanedText())
                : ReviewSentiment.NEGATIVE;
        String reviewId = cafeReviewRepository.nextReviewId();
        cafeReviewRepository.insert(reviewId, placeId, userId, request.content().trim(), cleanResult, sentiment);
        return cafeReviewRepository.findByPlaceId(placeId).stream()
                .filter(review -> review.reviewId().equals(reviewId))
                .findFirst()
                .map(CafeReview::toResponse)
                .orElseThrow(() -> new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "한줄평 저장 결과를 확인할 수 없습니다."));
    }

    private void assertCafe(String placeId) {
        if (!placeService.isCafe(placeId)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "카페에만 한줄평을 등록할 수 있습니다.");
        }
    }
}
