package com.studyspot.owner;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studyspot.common.ApiException;
import com.studyspot.place.PlaceRepository;
import com.studyspot.user.UserService;

@Service
public class OwnerVerificationService {

    private final OwnerVerificationRepository ownerVerificationRepository;
    private final PlaceRepository placeRepository;
    private final UserService userService;

    public OwnerVerificationService(OwnerVerificationRepository ownerVerificationRepository,
            PlaceRepository placeRepository, UserService userService) {
        this.ownerVerificationRepository = ownerVerificationRepository;
        this.placeRepository = placeRepository;
        this.userService = userService;
    }

    public List<OwnerVerificationResponse> findMine(String userId) {
        return ownerVerificationRepository.findByUserId(userId).stream()
                .map(OwnerVerification::toResponse)
                .toList();
    }

    public List<OwnerVerificationResponse> findAll(String status) {
        OwnerVerificationStatus parsedStatus = status == null || status.isBlank()
                ? null
                : OwnerVerificationStatus.from(status);
        return ownerVerificationRepository.findAll(parsedStatus).stream()
                .map(OwnerVerification::toResponse)
                .toList();
    }

    @Transactional
    public OwnerVerificationResponse requestVerification(String userId, OwnerVerificationRequest request) {
        VerificationPlace verificationPlace = resolveVerificationPlace(request.placeId());
        if (verificationPlace.existingPlaceId() != null
                && ownerVerificationRepository.hasPending(userId, verificationPlace.existingPlaceId())) {
            throw new ApiException(HttpStatus.CONFLICT, "이미 처리 대기 중인 사장 인증 요청이 있습니다.");
        }
        if (verificationPlace.requestedPlaceName() != null
                && ownerVerificationRepository.hasPendingRequestedPlace(userId, verificationPlace.requestedPlaceName())) {
            throw new ApiException(HttpStatus.CONFLICT, "이미 처리 대기 중인 사장 인증 요청이 있습니다.");
        }

        String verificationId = ownerVerificationRepository.nextVerificationId();
        ownerVerificationRepository.insert(verificationId, userId, verificationPlace.existingPlaceId(),
                verificationPlace.requestedPlaceName(), request);
        return findVerification(verificationId).toResponse();
    }

    @Transactional
    public OwnerVerificationResponse review(String verificationId, OwnerVerificationReviewRequest request) {
        OwnerVerification verification = findVerification(verificationId);
        OwnerVerificationStatus status = OwnerVerificationStatus.from(request.status());

        if (status == OwnerVerificationStatus.REJECTED
                && (request.rejectReason() == null || request.rejectReason().isBlank())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "반려 사유를 입력해주세요.");
        }

        ownerVerificationRepository.review(verificationId, status,
                status == OwnerVerificationStatus.REJECTED ? request.rejectReason() : null);

        if (status == OwnerVerificationStatus.APPROVED) {
            String placeId = verification.placeId();
            if (placeId == null || placeId.isBlank()) {
                placeId = placeRepository.nextPlaceId();
                placeRepository.insertPendingCafe(placeId, verification.requestedPlaceName());
                ownerVerificationRepository.updatePlaceId(verificationId, placeId);
            }
            userService.grantOwnerRole(verification.userId());
            placeRepository.addOwnerIfAbsent(verification.userId(), placeId);
        }

        return findVerification(verificationId).toResponse();
    }

    private OwnerVerification findVerification(String verificationId) {
        return ownerVerificationRepository.findById(verificationId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "사장 인증 요청을 찾을 수 없습니다."));
    }

    private VerificationPlace resolveVerificationPlace(String input) {
        String value = input == null ? "" : input.trim();
        if (value.isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "운영 카페를 입력해주세요.");
        }

        if (placeRepository.isCafe(value)) {
            return new VerificationPlace(value, null);
        }

        if (value.length() > 80) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "신규 카페명은 80자 이하로 입력해주세요.");
        }

        return new VerificationPlace(null, value);
    }

    private record VerificationPlace(String existingPlaceId, String requestedPlaceName) {
    }
}
