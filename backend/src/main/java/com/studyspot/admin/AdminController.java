package com.studyspot.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.studyspot.auth.AuthUser;
import com.studyspot.auth.LoginUser;
import com.studyspot.common.ApiException;
import com.studyspot.owner.OwnerVerificationResponse;
import com.studyspot.owner.OwnerVerificationReviewRequest;
import com.studyspot.owner.OwnerVerificationService;
import com.studyspot.place.PlaceService;
import com.studyspot.user.AdminUpdateUserRequest;
import com.studyspot.user.UserResponse;
import com.studyspot.user.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;
    private final PlaceService placeService;
    private final OwnerVerificationService ownerVerificationService;

    public AdminController(UserService userService, PlaceService placeService,
            OwnerVerificationService ownerVerificationService) {
        this.userService = userService;
        this.placeService = placeService;
        this.ownerVerificationService = ownerVerificationService;
    }

    @GetMapping("/summary")
    public AdminSummaryResponse summary(@LoginUser AuthUser authUser) {
        assertAdmin(authUser);
        return new AdminSummaryResponse(userService.countUsers(), placeService.countPlaces());
    }

    @GetMapping("/users")
    public List<UserResponse> findUsers(@LoginUser AuthUser authUser, @RequestParam(required = false) String keyword) {
        assertAdmin(authUser);
        return userService.findUsers(keyword);
    }

    @PatchMapping("/users/{userId}")
    public UserResponse updateUser(@LoginUser AuthUser authUser, @PathVariable String userId,
            @Valid @RequestBody AdminUpdateUserRequest request) {
        assertAdmin(authUser);
        return userService.updateByAdmin(userId, request);
    }

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@LoginUser AuthUser authUser, @PathVariable String userId) {
        assertAdmin(authUser);
        userService.deleteByAdmin(userId);
    }

    @GetMapping("/owner-verifications")
    public List<OwnerVerificationResponse> findOwnerVerifications(@LoginUser AuthUser authUser,
            @RequestParam(required = false) String status) {
        assertAdmin(authUser);
        return ownerVerificationService.findAll(status);
    }

    @PatchMapping("/owner-verifications/{verificationId}")
    public OwnerVerificationResponse reviewOwnerVerification(@LoginUser AuthUser authUser,
            @PathVariable String verificationId, @Valid @RequestBody OwnerVerificationReviewRequest request) {
        assertAdmin(authUser);
        return ownerVerificationService.review(verificationId, request);
    }

    private void assertAdmin(AuthUser authUser) {
        if (!authUser.isAdmin()) {
            throw new ApiException(HttpStatus.FORBIDDEN, "관리자 권한이 필요합니다.");
        }
    }
}
