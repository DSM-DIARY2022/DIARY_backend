package com.dsm.diary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DiaryBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiaryBackendApplication.class, args);
    }

}
