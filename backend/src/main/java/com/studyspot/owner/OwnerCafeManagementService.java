package com.studyspot.owner;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studyspot.auth.AuthUser;
import com.studyspot.common.ApiException;
import com.studyspot.place.PlaceRepository;

@Service
public class OwnerCafeManagementService {

    private static final int MAX_PHOTO_COUNT = 6;

    private final PlaceRepository placeRepository;
    private final CafeOccupancyStatusRepository occupancyStatusRepository;
    private final CafeProfileRepository cafeProfileRepository;
    private final CafePhotoRepository cafePhotoRepository;
    private final CafeOpenStatusRepository cafeOpenStatusRepository;

    public OwnerCafeManagementService(PlaceRepository placeRepository,
            CafeOccupancyStatusRepository occupancyStatusRepository,
            CafeProfileRepository cafeProfileRepository,
            CafePhotoRepository cafePhotoRepository,
            CafeOpenStatusRepository cafeOpenStatusRepository) {
        this.placeRepository = placeRepository;
        this.occupancyStatusRepository = occupancyStatusRepository;
        this.cafeProfileRepository = cafeProfileRepository;
        this.cafePhotoRepository = cafePhotoRepository;
        this.cafeOpenStatusRepository = cafeOpenStatusRepository;
    }

    public CafeOpenStatusResponse findOpenStatus(AuthUser authUser, String placeId) {
        assertCanManage(authUser, placeId);
        return findOpenStatus(placeId);
    }

    public CafeOpenStatusResponse findOpenStatus(String placeId) {
        return cafeOpenStatusRepository.findByPlaceId(placeId)
                .map(CafeOpenStatus::toResponse)
                .orElse(new CafeOpenStatus(placeId, false, "영업 상태가 등록되지 않았습니다.", null).toResponse());
    }

    @Transactional
    public CafeOpenStatusResponse saveOpenStatus(AuthUser authUser, String placeId, CafeOpenStatusRequest request) {
        assertCanManage(authUser, placeId);
        cafeOpenStatusRepository.upsert(placeId, request);
        return findOpenStatus(authUser, placeId);
    }

    public CafeOccupancyStatusResponse findStatus(AuthUser authUser, String placeId) {
        assertCanManage(authUser, placeId);
        return occupancyStatusRepository.findByPlaceId(placeId)
                .map(CafeOccupancyStatus::toResponse)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "등록된 실시간 인원 정보가 없습니다."));
    }

    @Transactional
    public CafeOccupancyStatusResponse saveStatus(AuthUser authUser, String placeId,
            CafeOccupancyStatusRequest request) {
        assertCanManage(authUser, placeId);
        CongestionLevel congestionLevel = request.congestionLevel() == null
                ? CongestionLevel.calculate(request.currentCount(), request.capacity())
                : CongestionLevel.from(request.congestionLevel());
        occupancyStatusRepository.upsert(placeId, request.currentCount(), request.capacity(), congestionLevel);
        return findStatus(authUser, placeId);
    }

    public CafeProfileResponse findProfile(AuthUser authUser, String placeId) {
        assertCanManage(authUser, placeId);
        return cafeProfileRepository.findByPlaceId(placeId)
                .map(CafeProfile::toResponse)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "등록된 카페 프로필이 없습니다."));
    }

    public CafeProfileResponse findPublicProfile(String placeId) {
        return cafeProfileRepository.findByPlaceId(placeId)
                .map(CafeProfile::toResponse)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "등록된 카페 프로필이 없습니다."));
    }

    @Transactional
    public CafeProfileResponse createProfile(AuthUser authUser, String placeId, CafeProfileRequest request) {
        assertCanManage(authUser, placeId);
        if (cafeProfileRepository.findByPlaceId(placeId).isPresent()) {
            throw new ApiException(HttpStatus.CONFLICT, "이미 카페 프로필이 등록되어 있습니다.");
        }
        cafeProfileRepository.insert(placeId, request);
        return findProfile(authUser, placeId);
    }

    @Transactional
    public CafeProfileResponse updateProfile(AuthUser authUser, String placeId, CafeProfileRequest request) {
        assertCanManage(authUser, placeId);
        cafeProfileRepository.findByPlaceId(placeId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "등록된 카페 프로필이 없습니다."));
        cafeProfileRepository.update(placeId, request);
        return findProfile(authUser, placeId);
    }

    @Transactional
    public void deleteProfile(AuthUser authUser, String placeId) {
        assertCanManage(authUser, placeId);
        cafeProfileRepository.delete(placeId);
    }

    public List<CafePhotoResponse> findPhotos(AuthUser authUser, String placeId) {
        assertCanManage(authUser, placeId);
        return cafePhotoRepository.findByPlaceId(placeId).stream()
                .map(CafePhoto::toResponse)
                .toList();
    }

    @Transactional
    public CafePhotoResponse addPhoto(AuthUser authUser, String placeId, CafePhotoCreateRequest request) {
        assertCanManage(authUser, placeId);
        if (cafePhotoRepository.countByPlaceId(placeId) >= MAX_PHOTO_COUNT) {
            throw new ApiException(HttpStatus.CONFLICT, "카페 사진은 최대 6개까지 등록할 수 있습니다.");
        }
        String photoId = cafePhotoRepository.nextPhotoId();
        cafePhotoRepository.insert(photoId, placeId, request);
        return findPhotoForCafe(placeId, photoId).toResponse();
    }

    @Transactional
    public CafePhotoResponse updatePhoto(AuthUser authUser, String placeId, String photoId,
            CafePhotoUpdateRequest request) {
        assertCanManage(authUser, placeId);
        findPhotoForCafe(placeId, photoId);
        cafePhotoRepository.update(photoId, request);
        return findPhotoForCafe(placeId, photoId).toResponse();
    }

    @Transactional
    public void deletePhoto(AuthUser authUser, String placeId, String photoId) {
        assertCanManage(authUser, placeId);
        findPhotoForCafe(placeId, photoId);
        cafePhotoRepository.delete(photoId);
    }

    private CafePhoto findPhotoForCafe(String placeId, String photoId) {
        CafePhoto photo = cafePhotoRepository.findById(photoId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "카페 사진을 찾을 수 없습니다."));
        if (!photo.placeId().equals(placeId)) {
            throw new ApiException(HttpStatus.NOT_FOUND, "카페 사진을 찾을 수 없습니다.");
        }
        return photo;
    }

    private void assertCanManage(AuthUser authUser, String placeId) {
        if (authUser.isAdmin()) {
            return;
        }
        if (!authUser.isOwner() || !placeRepository.isOwnerOfCafe(authUser.userId(), placeId)) {
            throw new ApiException(HttpStatus.FORBIDDEN, "본인 소유 카페만 관리할 수 있습니다.");
        }
    }
}
