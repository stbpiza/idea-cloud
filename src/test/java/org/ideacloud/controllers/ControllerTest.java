package org.ideacloud.controllers;

import org.ideacloud.IdeaCloudApplication;
import org.ideacloud.security.AccessTokenGenerator;
import org.ideacloud.security.AccessTokenService;
import org.ideacloud.security.AuthUser;
import org.ideacloud.security.AuthUserDao;
import org.ideacloud.security.WebSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ContextConfiguration(classes = {
        IdeaCloudApplication.class,
        WebSecurityConfig.class,
})
public abstract class ControllerTest {
    protected static final Long USER_ID = 1L;
    protected static final Long ADMIN_ID = 0L;

    @SpyBean
    private AccessTokenService accessTokenService;

    @SpyBean
    protected AccessTokenGenerator accessTokenGenerator;

    @MockBean
    protected AuthUserDao authUserDao;

    protected String userAccessToken;

    protected String adminAccessToken;

    @BeforeEach
    void setUpAccessTokenAndUserDetailsDaoForAuthentication() {
        userAccessToken = accessTokenGenerator.generate(USER_ID);
        adminAccessToken = accessTokenGenerator.generate(ADMIN_ID);

        AuthUser user = AuthUser.authenticated(
                USER_ID, "ROLE_USER", userAccessToken);

        given(authUserDao.findByAccessToken(userAccessToken))
                .willReturn(Optional.of(user));

        AuthUser admin = AuthUser.authenticated(
                ADMIN_ID, "ROLE_ADMIN", adminAccessToken);

        given(authUserDao.findByAccessToken(adminAccessToken))
                .willReturn(Optional.of(admin));
    }
}
