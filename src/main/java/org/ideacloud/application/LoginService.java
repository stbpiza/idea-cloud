package org.ideacloud.application;

import lombok.RequiredArgsConstructor;
import org.ideacloud.security.AccessTokenGenerator;
import org.ideacloud.security.AuthUser;
import org.ideacloud.security.AuthUserDao;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class LoginService {

    private final AuthUserDao authUserDao;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenGenerator accessTokenGenerator;

    public String login(String email, String password) {
        return authUserDao.findByEmail(email)
                .filter(authUser -> matchPassword(authUser, password))
                .map(this::generateAccessToken)
                .orElseThrow(() -> new BadCredentialsException("Login failed"));
    }

    private boolean matchPassword(AuthUser authUser, String password) {
        return passwordEncoder.matches(password, authUser.password());
    }

    private String generateAccessToken(AuthUser authUser) {
        String id = authUser.id();
        String accessToken = accessTokenGenerator.generate(id);
        authUserDao.addAccessToken(id, accessToken);
        return accessToken;
    }
}
