package com.dawist_o.authentication.registration;

import com.dawist_o.authentication.EmailValidator;
import com.dawist_o.authentication.mailSender.MailSender;
import com.dawist_o.authentication.mailSender.MailService;
import com.dawist_o.model.user.AppUser;
import com.dawist_o.model.user.AppUserRole;
import com.dawist_o.service.userService.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final AppUserService appUserService;
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
}
