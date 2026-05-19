package com.studyspot.auth;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.studyspot.common.ApiException;

@Service
public class AuthTokenService {

    private static final String DELIMITER = "\\.";

    @Value("${study-spot.auth.secret}")
    private String secret;

    @Value("${study-spot.auth.token-valid-minutes}")
    private long tokenValidMinutes;

    public String createToken(AuthUser user) {
        long expiresAt = Instant.now().plusSeconds(tokenValidMinutes * 60).getEpochSecond();
        String payload = user.userId() + "|" + user.name() + "|" + user.role() + "|" + expiresAt;
        String encodedPayload = base64Url(payload.getBytes(StandardCharsets.UTF_8));
        return encodedPayload + "." + sign(encodedPayload);
    }

    public AuthUser parseToken(String token) {
        String[] parts = token == null ? new String[0] : token.split(DELIMITER);
        if (parts.length != 2 || !sign(parts[0]).equals(parts[1])) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "로그인 정보가 유효하지 않습니다.");
        }

        try {
            String payload = new String(Base64.getUrlDecoder().decode(parts[0]), StandardCharsets.UTF_8);
            String[] values = payload.split("\\|", -1);
            if (values.length != 4) {
                throw new ApiException(HttpStatus.UNAUTHORIZED, "로그인 정보가 유효하지 않습니다.");
            }

            long expiresAt = Long.parseLong(values[3]);
            if (Instant.now().getEpochSecond() > expiresAt) {
                throw new ApiException(HttpStatus.UNAUTHORIZED, "로그인 시간이 만료되었습니다.");
            }

            return new AuthUser(values[0], values[1], values[2]);
        } catch (IllegalArgumentException exception) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "로그인 정보가 유효하지 않습니다.");
        }
    }

    private String sign(String value) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            return base64Url(mac.doFinal(value.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception exception) {
            throw new IllegalStateException("Could not sign auth token.", exception);
        }
    }

    private String base64Url(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
