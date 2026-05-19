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
import com.studyspot.user.AdminUpdateUserRequest;
import com.studyspot.user.UserResponse;
import com.studyspot.user.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/users")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> findUsers(@LoginUser AuthUser authUser, @RequestParam(required = false) String keyword) {
        assertAdmin(authUser);
        return userService.findUsers(keyword);
    }

    @PatchMapping("/{userId}")
    public UserResponse updateUser(@LoginUser AuthUser authUser, @PathVariable String userId,
            @Valid @RequestBody AdminUpdateUserRequest request) {
        assertAdmin(authUser);
        return userService.updateByAdmin(userId, request);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@LoginUser AuthUser authUser, @PathVariable String userId) {
        assertAdmin(authUser);
        userService.deleteByAdmin(userId);
    }

    private void assertAdmin(AuthUser authUser) {
        if (!authUser.isAdmin()) {
            throw new ApiException(HttpStatus.FORBIDDEN, "관리자 권한이 필요합니다.");
        }
    }
}
