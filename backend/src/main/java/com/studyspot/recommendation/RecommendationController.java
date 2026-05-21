package com.studyspot.recommendation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studyspot.place.PlaceResponse;
import com.studyspot.place.PlaceService;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final PlaceService placeService;

    public RecommendationController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("/places")
    public List<PlaceResponse> recommendPlaces(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String wifiStatus,
            @RequestParam(required = false) String outletStatus,
            @RequestParam(required = false) String noiseLevel,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return placeService.recommend(type, keyword, wifiStatus, outletStatus, noiseLevel, limit);
    }

    @GetMapping("/cafes")
    public List<PlaceResponse> recommendCafes(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String wifiStatus,
            @RequestParam(required = false) String outletStatus,
            @RequestParam(required = false) String noiseLevel,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return placeService.recommend("cafe", keyword, wifiStatus, outletStatus, noiseLevel, limit);
    }
}
