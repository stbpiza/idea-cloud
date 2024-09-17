package org.ideacloud.backdoor;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "00.Backdoor", description = "백도어 API (테스트용 초기 데이터 세팅)")
@RequiredArgsConstructor
@RestController
@RequestMapping("/backdoor")
public class BackdoorController {

    private final BackdoorService backdoorService;


    @GetMapping("/setup-database")
    @Transactional
    public String setupDatabase() {
        backdoorService.setupDatabase();
        return "Database setup complete";
    }
}
