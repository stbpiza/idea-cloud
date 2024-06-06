package org.ideacloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class IdeaCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdeaCloudApplication.class, args);
    }

}
