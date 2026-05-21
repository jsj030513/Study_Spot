package com.studyspot.place;

import java.util.Comparator;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studyspot.auth.AuthUser;
import com.studyspot.common.ApiException;
import com.studyspot.owner.OwnerCafeUpdateRequest;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public List<PlaceResponse> findPlaces(String type, String keyword, String wifiStatus, String outletStatus,
            String noiseLevel) {
        return placeRepository.findAll(parseType(type), keyword, wifiStatus, outletStatus, noiseLevel).stream()
                .map(Place::toResponse)
                .toList();
    }

    public PlaceResponse findPlace(String placeId) {
        return PlaceResponse.from(findPlaceEntity(placeId));
    }

    public boolean isCafe(String placeId) {
        return findPlaceEntity(placeId).type() == PlaceType.CAFE;
    }

    public long countPlaces() {
        return placeRepository.count();
    }

    public List<PlaceResponse> recommend(String type, String keyword, String wifiStatus, String outletStatus,
            String noiseLevel, int limit) {
        int safeLimit = Math.max(1, Math.min(limit, 50));
        return findPlaces(type, keyword, wifiStatus, outletStatus, noiseLevel).stream()
                .sorted(Comparator.comparingInt(PlaceResponse::recommendScore).reversed())
                .limit(safeLimit)
                .toList();
    }

    public List<PlaceResponse> findOwnerCafes(String ownerUserId) {
        return placeRepository.findOwnerCafes(ownerUserId).stream()
                .map(Place::toResponse)
                .toList();
    }

    @Transactional
    public PlaceResponse create(PlaceCreateRequest request) {
        String placeId = placeRepository.nextPlaceId();
        placeRepository.insert(placeId, request);
        return findPlace(placeId);
    }

    @Transactional
    public PlaceResponse update(String placeId, PlaceUpdateRequest request) {
        findPlaceEntity(placeId);
        placeRepository.update(placeId, request);
        return findPlace(placeId);
    }

    @Transactional
    public PlaceResponse updateOwnerCafe(AuthUser authUser, String placeId, OwnerCafeUpdateRequest request) {
        if (!authUser.isAdmin() && !placeRepository.isOwnerOfCafe(authUser.userId(), placeId)) {
            throw new ApiException(HttpStatus.FORBIDDEN, "본인 소유 카페만 수정할 수 있습니다.");
        }

        PlaceUpdateRequest updateRequest = new PlaceUpdateRequest(
                request.name(),
                null,
                request.latitude(),
                request.longitude(),
                request.address(),
                request.telNo(),
                request.wifiStatus(),
                request.outletStatus(),
                request.noiseLevel(),
                request.seatType(),
                request.description()
        );
        placeRepository.update(placeId, updateRequest);
        return findPlace(placeId);
    }

    @Transactional
    public void delete(String placeId) {
        findPlaceEntity(placeId);
        placeRepository.delete(placeId);
    }

    private Place findPlaceEntity(String placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "장소를 찾을 수 없습니다."));
    }

    private PlaceType parseType(String type) {
        if (type == null || type.isBlank() || "all".equalsIgnoreCase(type)) {
            return null;
        }
        return PlaceType.from(type.trim());
    }
}
