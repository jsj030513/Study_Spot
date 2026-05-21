package com.studyspot.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studyspot.auth.AuthTokenService;
import com.studyspot.auth.AuthUser;
import com.studyspot.common.ApiException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthTokenService tokenService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository, AuthTokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Transactional
    public UserResponse signup(SignupRequest request) {
        if (userRepository.existsById(request.userId())) {
            throw new ApiException(HttpStatus.CONFLICT, "이미 사용 중인 아이디입니다.");
        }

        String role = userRepository.count() == 0 ? "A" : "U";
        userRepository.save(request.userId(), passwordEncoder.encode(request.password()), request.name(), role);
        return UserResponse.from(findUser(request.userId()));
    }

    public LoginResponse login(LoginRequest request) {
        User user = findUser(request.userId());
        if (!passwordEncoder.matches(request.password(), user.password())) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        String token = tokenService.createToken(new AuthUser(user.userId(), user.name(), user.role()));
        return new LoginResponse(token, UserResponse.from(user));
    }

    public UserIdAvailabilityResponse checkUserIdAvailability(String userId) {
        String normalizedUserId = blankToNull(userId);
        if (normalizedUserId == null || !normalizedUserId.matches("^[A-Za-z0-9_]{4,20}$")) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "아이디는 영문, 숫자, 밑줄로 4~20자여야 합니다.");
        }
        return new UserIdAvailabilityResponse(normalizedUserId, !userRepository.existsById(normalizedUserId));
    }

    public UserResponse getMe(String userId) {
        return UserResponse.from(findUser(userId));
    }

    @Transactional
    public UserResponse updateMe(String userId, UpdateMeRequest request) {
        String name = blankToNull(request.name());
        String encodedPassword = request.password() == null || request.password().isBlank()
                ? null
                : passwordEncoder.encode(request.password());

        if (name == null && encodedPassword == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "수정할 값을 입력해주세요.");
        }

        userRepository.updateMe(userId, name, encodedPassword);
        return UserResponse.from(findUser(userId));
    }

    @Transactional
    public void deleteMe(String userId) {
        userRepository.delete(userId);
    }

    public List<UserResponse> findUsers(String keyword) {
        return userRepository.findAll(keyword).stream().map(UserResponse::from).toList();
    }

    public long countUsers() {
        return userRepository.count();
    }

    @Transactional
    public void grantOwnerRole(String userId) {
        findUser(userId);
        userRepository.updateRole(userId, "O");
    }

    @Transactional
    public UserResponse updateByAdmin(String userId, AdminUpdateUserRequest request) {
        findUser(userId);
        String name = blankToNull(request.name());
        String role = blankToNull(request.role());

        if (name == null && role == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "수정할 값을 입력해주세요.");
        }

        userRepository.updateByAdmin(userId, name, role);
        return UserResponse.from(findUser(userId));
    }

    @Transactional
    public void deleteByAdmin(String userId) {
        findUser(userId);
        userRepository.delete(userId);
    }

    private User findUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."));
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
