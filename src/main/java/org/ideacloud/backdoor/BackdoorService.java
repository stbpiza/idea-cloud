package org.ideacloud.backdoor;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.ideacloud.models.MeetingNote;
import org.ideacloud.models.Role;
import org.ideacloud.models.User;
import org.ideacloud.repositories.MeetingNoteRepository;
import org.ideacloud.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BackdoorService {

    private final UserRepository userRepository;
    private final MeetingNoteRepository meetingNoteRepository;
    private final EntityManager em;


    private final String TEST_USER_NAME = "tester";
    private final String TEST_USER_EMAIL = "@test.com";
    private final String TEST_USER_PASSWORD = "12341234";

    public void setupDatabase() {

        if (notExistUserSetupData()) {
            setupUser();
        }

        if (notExistMeetingNoteSetupData()) {
            setupMeetingNote();
        }

    }


    public boolean notExistUserSetupData() {
        return !userRepository.existsByEmail(TEST_USER_NAME + "1" + TEST_USER_EMAIL);
    }

    public void setupUser() {

        List<User> users = new ArrayList<>();

        for (int i=1; i<=10; i++) {
            users.add(User.builder()
                    .email(TEST_USER_NAME + i + TEST_USER_EMAIL)
                    .name(TEST_USER_NAME + i)
                    .password(TEST_USER_PASSWORD)
                    .role(Role.ROLE_USER)
                    .build());
        }

        userRepository.saveAll(users);
    }

    public boolean notExistMeetingNoteSetupData() {
        return meetingNoteRepository.count() == 0;
    }

    public void setupMeetingNote() {

        List<MeetingNote> meetingNotes = new ArrayList<>();

        for (int i=1; i<=3; i++) {
            meetingNotes.add(MeetingNote.builder()
                    .title("회의록 제목 " + i)
                    .body("회의록 내용 " + i)
                    .user(em.getReference(User.class, 1L))
                    .build());
        }

        meetingNoteRepository.saveAll(meetingNotes);

    }
}
