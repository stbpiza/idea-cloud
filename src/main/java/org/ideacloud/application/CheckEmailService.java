package org.ideacloud.application;

import lombok.RequiredArgsConstructor;
import org.ideacloud.exceptions.EmailAlreadyTaken;
import org.ideacloud.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CheckEmailService {

    private final UserRepository userRepository;

    public void checkEmail(String email) {
        boolean isExistsed = userRepository.existsByEmail(email);

        if (isExistsed) {
            throw new EmailAlreadyTaken(email);
        }
    }

}
