package com.studyspot.owner;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.studyspot.auth.AuthUser;
import com.studyspot.auth.LoginUser;
import com.studyspot.common.ApiException;
import com.studyspot.place.PlaceResponse;
import com.studyspot.place.PlaceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/owner/cafes")
public class OwnerCafeController {

    private final PlaceService placeService;
    private final OwnerCafeManagementService ownerCafeManagementService;

    public OwnerCafeController(PlaceService placeService, OwnerCafeManagementService ownerCafeManagementService) {
        this.placeService = placeService;
        this.ownerCafeManagementService = ownerCafeManagementService;
    }

    @GetMapping
    public List<PlaceResponse> findMyCafes(@LoginUser AuthUser authUser) {
        assertOwner(authUser);
        return placeService.findOwnerCafes(authUser.userId());
    }

    @PatchMapping("/{placeId}")
    public PlaceResponse updateMyCafe(@LoginUser AuthUser authUser, @PathVariable String placeId,
            @Valid @RequestBody OwnerCafeUpdateRequest request) {
        assertOwner(authUser);
        return placeService.updateOwnerCafe(authUser, placeId, request);
    }

    @GetMapping("/{placeId}/open-status")
    public CafeOpenStatusResponse findOpenStatus(@LoginUser AuthUser authUser, @PathVariable String placeId) {
        assertOwner(authUser);
        return ownerCafeManagementService.findOpenStatus(authUser, placeId);
    }

    @PostMapping("/{placeId}/open-status")
    @ResponseStatus(HttpStatus.CREATED)
    public CafeOpenStatusResponse createOpenStatus(@LoginUser AuthUser authUser, @PathVariable String placeId,
            @Valid @RequestBody CafeOpenStatusRequest request) {
        assertOwner(authUser);
        return ownerCafeManagementService.saveOpenStatus(authUser, placeId, request);
    }

    @PatchMapping("/{placeId}/open-status")
    public CafeOpenStatusResponse updateOpenStatus(@LoginUser AuthUser authUser, @PathVariable String placeId,
            @Valid @RequestBody CafeOpenStatusRequest request) {
        assertOwner(authUser);
        return ownerCafeManagementService.saveOpenStatus(authUser, placeId, request);
    }

    @GetMapping("/{placeId}/status")
    public CafeOccupancyStatusResponse findStatus(@LoginUser AuthUser authUser, @PathVariable String placeId) {
        assertOwner(authUser);
        return ownerCafeManagementService.findStatus(authUser, placeId);
    }

    @PostMapping("/{placeId}/status")
    @ResponseStatus(HttpStatus.CREATED)
    public CafeOccupancyStatusResponse createStatus(@LoginUser AuthUser authUser, @PathVariable String placeId,
            @Valid @RequestBody CafeOccupancyStatusRequest request) {
        assertOwner(authUser);
        return ownerCafeManagementService.saveStatus(authUser, placeId, request);
    }

    @PatchMapping("/{placeId}/status")
    public CafeOccupancyStatusResponse updateStatus(@LoginUser AuthUser authUser, @PathVariable String placeId,
            @Valid @RequestBody CafeOccupancyStatusRequest request) {
        assertOwner(authUser);
        return ownerCafeManagementService.saveStatus(authUser, placeId, request);
    }

    @GetMapping("/{placeId}/profile")
    public CafeProfileResponse findProfile(@LoginUser AuthUser authUser, @PathVariable String placeId) {
        assertOwner(authUser);
        return ownerCafeManagementService.findProfile(authUser, placeId);
    }

    @PostMapping("/{placeId}/profile")
    @ResponseStatus(HttpStatus.CREATED)
    public CafeProfileResponse createProfile(@LoginUser AuthUser authUser, @PathVariable String placeId,
            @Valid @RequestBody CafeProfileRequest request) {
        assertOwner(authUser);
        return ownerCafeManagementService.createProfile(authUser, placeId, request);
    }

    @PatchMapping("/{placeId}/profile")
    public CafeProfileResponse updateProfile(@LoginUser AuthUser authUser, @PathVariable String placeId,
            @Valid @RequestBody CafeProfileRequest request) {
        assertOwner(authUser);
        return ownerCafeManagementService.updateProfile(authUser, placeId, request);
    }

    @DeleteMapping("/{placeId}/profile")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@LoginUser AuthUser authUser, @PathVariable String placeId) {
        assertOwner(authUser);
        ownerCafeManagementService.deleteProfile(authUser, placeId);
    }

    @GetMapping("/{placeId}/photos")
    public List<CafePhotoResponse> findPhotos(@LoginUser AuthUser authUser, @PathVariable String placeId) {
        assertOwner(authUser);
        return ownerCafeManagementService.findPhotos(authUser, placeId);
    }

    @PostMapping("/{placeId}/photos")
    @ResponseStatus(HttpStatus.CREATED)
    public CafePhotoResponse addPhoto(@LoginUser AuthUser authUser, @PathVariable String placeId,
            @Valid @RequestBody CafePhotoCreateRequest request) {
        assertOwner(authUser);
        return ownerCafeManagementService.addPhoto(authUser, placeId, request);
    }

    @PatchMapping("/{placeId}/photos/{photoId}")
    public CafePhotoResponse updatePhoto(@LoginUser AuthUser authUser, @PathVariable String placeId,
            @PathVariable String photoId, @Valid @RequestBody CafePhotoUpdateRequest request) {
        assertOwner(authUser);
        return ownerCafeManagementService.updatePhoto(authUser, placeId, photoId, request);
    }

    @DeleteMapping("/{placeId}/photos/{photoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePhoto(@LoginUser AuthUser authUser, @PathVariable String placeId, @PathVariable String photoId) {
        assertOwner(authUser);
        ownerCafeManagementService.deletePhoto(authUser, placeId, photoId);
    }

    private void assertOwner(AuthUser authUser) {
        if (!authUser.isOwner() && !authUser.isAdmin()) {
            throw new ApiException(HttpStatus.FORBIDDEN, "사장님 권한이 필요합니다.");
        }
    }
}
