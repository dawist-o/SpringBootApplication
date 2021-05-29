package com.dawist_o.authentication.registration;

import com.dawist_o.authentication.EmailValidator;
import com.dawist_o.authentication.mailSender.MailSender;
import com.dawist_o.authentication.mailSender.MailService;
import com.dawist_o.authentication.token.ConfirmationToken;
import com.dawist_o.authentication.token.ConfirmationTokenService;
import com.dawist_o.model.user.AppUser;
import com.dawist_o.model.user.AppUserRole;
import com.dawist_o.service.userService.AppUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class RegistrationService {
    private final AppUserService appUserService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailValidator emailValidator;
    private final MailSender mailService;

    //TODO: make address injected from properties file
    private static final String address = "http://localhost:8080/auth/confirm?token=";

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        String token = appUserService.signUpUser(new AppUser(
                request.getName(),
                request.getEmail(),
                request.getPass(),
                AppUserRole.USER
        ));
        String link = address + token;
        mailService.send("vkoval2002@gmail.com", link);
        System.out.println("address " + address);

        return "works";
    }

    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());

        return "confirmed";
    }
}
