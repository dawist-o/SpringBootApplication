package com.dawist_o.service.userService;

import com.dawist_o.authentication.token.ConfirmationToken;
import com.dawist_o.authentication.token.ConfirmationTokenService;
import com.dawist_o.dao.appUser.AppUserRepository;
import com.dawist_o.model.user.AppUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class AppUserService implements UserDetailsService {
    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";

    private final AppUserRepository appUserRepository;
    //TODO replace token service into registration
    private final ConfirmationTokenService confirmationTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public AppUser loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("In AppUserService method loadUserByUsername: " + email);
        return appUserRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository
                .findByEmail(appUser.getEmail())
                .isPresent();

        if (userExists) {
            throw new IllegalStateException("email already taken");
        }

        //encode password for store in database
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        appUserRepository.save(appUser);
        log.info("In AppUserService method signUpUser: " + appUser);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken =
                new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), appUser);

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public int enableAppUser(String email) {
        log.info("In AppUserService method enableUserEmail: " + email);
        return appUserRepository.enableUserEmail(email);
    }

    public String changePassword(Principal principal, String pass, String newPass, String repeatedPass) {
        if (!newPass.equals(repeatedPass))
            return "New password doesnt equal repeated";

        AppUser user = appUserRepository.findByEmail(principal.getName()).get();

        if(!bCryptPasswordEncoder.matches(pass,user.getPassword())){
            return "Invalid old password";
        }

        user.setPassword(bCryptPasswordEncoder.encode(newPass));
        appUserRepository.save(user);

        log.info("In AppUserService method changePassword: " + user);
        return "";
    }
}
