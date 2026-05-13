package com.kostinartem.courseplatform.service;

import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KostinArtemAsyncService {

    private final JavaMailSender mailSender;

    @Async
    public void sendWelcomeEmail(String email, String fullName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Welcome to ArtemKostinSession");
        message.setText("Hello, " + fullName + "! Welcome to the Online Course Platform.");
        mailSender.send(message);
        log.info("Welcome email sent to {}", email);
    }

    @Async
    public void logEnrollment(Long userId, Long courseId) {
        log.info("Async enrollment log: userId={}, courseId={}", userId, courseId);
    }

    @Async
    public CompletableFuture<String> calculateFakeStatistics(Long courseId) {
        String result = "Statistics calculated for course " + courseId;
        log.info(result);
        return CompletableFuture.completedFuture(result);
    }
}
