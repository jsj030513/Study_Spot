package com.studyspot.cafe;

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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cafes")
public class CafeController {

    private final CafeService cafeService;

    public CafeController(CafeService cafeService) {
        this.cafeService = cafeService;
    }

    @GetMapping
    public List<CafeResponse> findCafes(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String wifiStatus,
            @RequestParam(required = false) String outletFlag,
            @RequestParam(required = false) String noiseLevel
    ) {
        return cafeService.findCafes(keyword, wifiStatus, outletFlag, noiseLevel);
    }

    @GetMapping("/{cafeId}")
    public CafeResponse findCafe(@PathVariable String cafeId) {
        return cafeService.findCafe(cafeId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CafeResponse createCafe(@LoginUser AuthUser authUser, @Valid @RequestBody CafeCreateRequest request) {
        assertAdmin(authUser);
        return cafeService.create(request);
    }

    @PatchMapping("/{cafeId}")
    public CafeResponse updateCafe(@LoginUser AuthUser authUser, @PathVariable String cafeId,
            @Valid @RequestBody CafeUpdateRequest request) {
        assertAdmin(authUser);
        return cafeService.update(cafeId, request);
    }

    @DeleteMapping("/{cafeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCafe(@LoginUser AuthUser authUser, @PathVariable String cafeId) {
        assertAdmin(authUser);
        cafeService.delete(cafeId);
    }

    private void assertAdmin(AuthUser authUser) {
        if (!authUser.isAdmin()) {
            throw new ApiException(HttpStatus.FORBIDDEN, "관리자 권한이 필요합니다.");
        }
    }
}
