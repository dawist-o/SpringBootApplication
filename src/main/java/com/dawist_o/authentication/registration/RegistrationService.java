package com.dawist_o.authentication.registration;

import com.dawist_o.authentication.EmailValidator;
import com.dawist_o.authentication.exceptions.InvalidRegistrationException;
import com.dawist_o.authentication.mailSender.MailSender;
import com.dawist_o.authentication.token.ConfirmationToken;
import com.dawist_o.authentication.token.ConfirmationTokenService;
import com.dawist_o.model.user.AppUser;
import com.dawist_o.model.user.AppUserRole;
import com.dawist_o.service.userService.AppUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class RegistrationService {
    private final AppUserService appUserService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailValidator emailValidator;
    private final MailSender mailService;

    public String register(HttpServletRequest httpRequest, RegistrationRequest request) throws IllegalStateException{
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail) {
            throw new InvalidRegistrationException("Email is not valid");
        }
        if(!request.getPass().equals(request.getRe_pass())){
            throw new InvalidRegistrationException("Both passwords should be equal");
        }

        String token = appUserService.signUpUser(new AppUser(
                request.getName(),
                request.getEmail(),
                request.getPass(),
                AppUserRole.USER
        ));

        String address = httpRequest.getRequestURL().toString().replace(httpRequest.getRequestURI(), "");
        String link = address + "/auth/confirm?token=" + token;
        mailService.send(request.getEmail(), link);

        log.info("In RegistrationService send mail with token: " + link);
        return link;
    }

    @Transactional
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
