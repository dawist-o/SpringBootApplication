package com.dawist_o.authentication.token;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {
        log.info("In ConfirmationTokenService method save: " + token);
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        log.info("In ConfirmationTokenService method getToken: " + token);
        return confirmationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        log.info("In ConfirmationTokenService method setConfirmedAt: " + token);
        return confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
