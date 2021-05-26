package com.dawist_o.registration;

import com.dawist_o.model.user.AppUser;
import com.dawist_o.model.user.AppUserRole;
import com.dawist_o.service.userService.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final AppUserService appUserService;
    private final EmailValidator emailValidator;

    public String register(RegistrationRequest request){
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

        return "works";
    }
}
