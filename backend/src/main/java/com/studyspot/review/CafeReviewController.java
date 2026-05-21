package com.studyspot.review;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.studyspot.auth.AuthUser;
import com.studyspot.auth.LoginUser;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/places/{placeId}/reviews")
public class CafeReviewController {

    private final CafeReviewService cafeReviewService;

    public CafeReviewController(CafeReviewService cafeReviewService) {
        this.cafeReviewService = cafeReviewService;
    }

    @GetMapping
    public List<CafeReviewResponse> findCafeReviews(@PathVariable String placeId) {
        return cafeReviewService.findCafeReviews(placeId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CafeReviewResponse createCafeReview(@LoginUser AuthUser authUser, @PathVariable String placeId,
            @Valid @RequestBody CafeReviewCreateRequest request) {
        return cafeReviewService.createCafeReview(placeId, authUser.userId(), request);
    }
}
