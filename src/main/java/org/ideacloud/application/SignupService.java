package org.ideacloud.application;

import lombok.RequiredArgsConstructor;
import org.ideacloud.exceptions.EmailAlreadyTaken;
import org.ideacloud.models.User;
import org.ideacloud.repositories.UserRepository;
import org.ideacloud.security.AccessTokenGenerator;
import org.ideacloud.security.AuthUserDao;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.ideacloud.models.Role.ROLE_USER;

@RequiredArgsConstructor
@Transactional
@Service
public class SignupService {

    private final AuthUserDao authUserDao;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenGenerator accessTokenGenerator;
    private final UserRepository userRepository;

    public String signup(String email, String name, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyTaken(email);
        }

        User user = createUser(email, name, password);

        return createAccessToken(user.getUserId());
    }

    private User createUser(String email, String name, String password) {

        User user = User.builder()
                .email(email)
                .name(name)
                .password(passwordEncoder.encode(password))
                .role(ROLE_USER)
                .build();

        userRepository.save(user);

        return user;
    }

    private String createAccessToken(Long userId) {
        String accessToken = accessTokenGenerator.generate(userId.toString());

        authUserDao.addAccessToken(userId.toString(), accessToken);

        return accessToken;
    }
}
