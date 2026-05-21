package com.studyspot.owner;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.studyspot.auth.AuthUser;
import com.studyspot.auth.LoginUser;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/owner/verifications")
public class OwnerVerificationController {

    private final OwnerVerificationService ownerVerificationService;

    public OwnerVerificationController(OwnerVerificationService ownerVerificationService) {
        this.ownerVerificationService = ownerVerificationService;
    }

    @GetMapping("/me")
    public List<OwnerVerificationResponse> findMine(@LoginUser AuthUser authUser) {
        return ownerVerificationService.findMine(authUser.userId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OwnerVerificationResponse requestVerification(@LoginUser AuthUser authUser,
            @Valid @RequestBody OwnerVerificationRequest request) {
        return ownerVerificationService.requestVerification(authUser.userId(), request);
    }
}
