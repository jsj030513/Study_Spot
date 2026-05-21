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
        assertCafe(request.placeId());
        if (ownerVerificationRepository.hasPending(userId, request.placeId())) {
            throw new ApiException(HttpStatus.CONFLICT, "이미 처리 대기 중인 사장 인증 요청이 있습니다.");
        }

        String verificationId = ownerVerificationRepository.nextVerificationId();
        ownerVerificationRepository.insert(verificationId, userId, request);
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
            placeRepository.addOwnerIfAbsent(verification.userId(), verification.placeId());
            userService.grantOwnerRole(verification.userId());
        }

        return findVerification(verificationId).toResponse();
    }

    private OwnerVerification findVerification(String verificationId) {
        return ownerVerificationRepository.findById(verificationId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "사장 인증 요청을 찾을 수 없습니다."));
    }

    private void assertCafe(String placeId) {
        if (!placeRepository.isCafe(placeId)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "카페에 대해서만 사장 인증을 요청할 수 있습니다.");
        }
    }
}
