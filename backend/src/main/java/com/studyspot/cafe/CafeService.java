package com.studyspot.cafe;

import java.util.Comparator;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studyspot.common.ApiException;

@Service
public class CafeService {

    private final CafeRepository cafeRepository;

    public CafeService(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    public List<CafeResponse> findCafes(String keyword, String wifiStatus, String outletFlag, String noiseLevel) {
        return cafeRepository.findAll(keyword, wifiStatus, outletFlag, noiseLevel).stream()
                .map(CafeResponse::from)
                .toList();
    }

    public CafeResponse findCafe(String cafeId) {
        return CafeResponse.from(findCafeEntity(cafeId));
    }

    public List<CafeResponse> recommend(String keyword, String wifiStatus, String outletFlag, String noiseLevel, int limit) {
        int safeLimit = Math.max(1, Math.min(limit, 50));
        return cafeRepository.findAll(keyword, wifiStatus, outletFlag, noiseLevel).stream()
                .map(CafeResponse::from)
                .sorted(Comparator.comparingInt(CafeResponse::recommendScore).reversed())
                .limit(safeLimit)
                .toList();
    }

    @Transactional
    public CafeResponse create(CafeCreateRequest request) {
        String cafeId = cafeRepository.nextCafeId();
        cafeRepository.insert(cafeId, request);
        return findCafe(cafeId);
    }

    @Transactional
    public CafeResponse update(String cafeId, CafeUpdateRequest request) {
        findCafeEntity(cafeId);
        cafeRepository.updateInfo(cafeId, request);
        return findCafe(cafeId);
    }

    @Transactional
    public void delete(String cafeId) {
        findCafeEntity(cafeId);
        cafeRepository.delete(cafeId);
    }

    private Cafe findCafeEntity(String cafeId) {
        return cafeRepository.findById(cafeId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "장소를 찾을 수 없습니다."));
    }
}
