package com.example.diary_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class DiaryBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiaryBackendApplication.class, args);
    }

}
