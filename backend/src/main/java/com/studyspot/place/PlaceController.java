package com.studyspot.place;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.studyspot.auth.AuthUser;
import com.studyspot.auth.LoginUser;
import com.studyspot.common.ApiException;
import com.studyspot.owner.CafeProfileResponse;
import com.studyspot.owner.CafeOpenStatusResponse;
import com.studyspot.owner.OwnerCafeManagementService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/places")
public class PlaceController {

    private final PlaceService placeService;
    private final OwnerCafeManagementService ownerCafeManagementService;

    public PlaceController(PlaceService placeService, OwnerCafeManagementService ownerCafeManagementService) {
        this.placeService = placeService;
        this.ownerCafeManagementService = ownerCafeManagementService;
    }

    @GetMapping
    public List<PlaceResponse> findPlaces(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String wifiStatus,
            @RequestParam(required = false) String outletStatus,
            @RequestParam(required = false) String noiseLevel
    ) {
        return placeService.findPlaces(type, keyword, wifiStatus, outletStatus, noiseLevel);
    }

    @GetMapping("/{placeId}")
    public PlaceResponse findPlace(@PathVariable String placeId) {
        return placeService.findPlace(placeId);
    }

    @GetMapping("/{placeId}/open-status")
    public CafeOpenStatusResponse findOpenStatus(@PathVariable String placeId) {
        if (!placeService.isCafe(placeId)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "카페에만 오픈 여부가 제공됩니다.");
        }
        return ownerCafeManagementService.findOpenStatus(placeId);
    }

    @GetMapping("/{placeId}/profile")
    public CafeProfileResponse findProfile(@PathVariable String placeId) {
        if (!placeService.isCafe(placeId)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "카페에만 프로필이 제공됩니다.");
        }
        return ownerCafeManagementService.findPublicProfile(placeId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlaceResponse createPlace(@LoginUser AuthUser authUser, @Valid @RequestBody PlaceCreateRequest request) {
        assertAdmin(authUser);
        return placeService.create(request);
    }

    @PatchMapping("/{placeId}")
    public PlaceResponse updatePlace(@LoginUser AuthUser authUser, @PathVariable String placeId,
            @Valid @RequestBody PlaceUpdateRequest request) {
        assertAdmin(authUser);
        return placeService.update(placeId, request);
    }

    @DeleteMapping("/{placeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlace(@LoginUser AuthUser authUser, @PathVariable String placeId) {
        assertAdmin(authUser);
        placeService.delete(placeId);
    }

    private void assertAdmin(AuthUser authUser) {
        if (!authUser.isAdmin()) {
            throw new ApiException(HttpStatus.FORBIDDEN, "관리자 권한이 필요합니다.");
        }
    }
}
