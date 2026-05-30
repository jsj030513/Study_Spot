package com.studyspot.place;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cafes")
public class CafeCompatibilityController {

    private final PlaceService placeService;

    public CafeCompatibilityController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping
    public List<PlaceResponse> findCafes(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String wifiStatus,
            @RequestParam(required = false) String outletStatus,
            @RequestParam(required = false) String noiseLevel
    ) {
        return placeService.findPlaces("cafe", keyword, wifiStatus, outletStatus, noiseLevel);
    }

    @GetMapping("/{placeId}")
    public PlaceResponse findCafe(@PathVariable String placeId) {
        PlaceResponse place = placeService.findPlace(placeId);
        if (!"cafe".equals(place.type())) {
            throw new com.studyspot.common.ApiException(
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "카페가 아닌 장소입니다."
            );
        }
        return place;
    }
}
