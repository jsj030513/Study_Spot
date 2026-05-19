package com.studyspot.recommendation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studyspot.cafe.CafeResponse;
import com.studyspot.cafe.CafeService;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final CafeService cafeService;

    public RecommendationController(CafeService cafeService) {
        this.cafeService = cafeService;
    }

    @GetMapping("/cafes")
    public List<CafeResponse> recommendCafes(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String wifiStatus,
            @RequestParam(required = false) String outletFlag,
            @RequestParam(required = false) String noiseLevel,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return cafeService.recommend(keyword, wifiStatus, outletFlag, noiseLevel, limit);
    }
}
