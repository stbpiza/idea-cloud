package org.ideacloud.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class AccessTokenGenerator {
    private final Algorithm algorithm;

    public AccessTokenGenerator(
            @Value("${jwt.secret}")
            String secret
    ) {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public String generate(Long userId) {
        return JWT.create()
                .withClaim("userId", userId)
                .withExpiresAt(Instant.now().plus(365, ChronoUnit.DAYS))
                .sign(algorithm);
    }

    public boolean verify(String accessToken) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(accessToken);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}
