package com.bladebackend.blade.customer.usecases;

import com.bladebackend.blade.customer.domains.AppUser;
import com.bladebackend.blade.customer.gateways.AppUserRepository;
import com.bladebackend.blade.customer.gateways.token.ConfirmationToken;
import com.bladebackend.blade.customer.gateways.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG,email)));
    }

    public void enableAppUser(String email){
        final AppUser appUser = appUserRepository.findByEmail(email)
                                        .orElseThrow(()
                                        -> new IllegalStateException(""));
        appUser.setEnable(true);

        appUserRepository.save(appUser);
    }
    public String signUpUser(AppUser appUser){
        Optional<AppUser> user = appUserRepository.findByEmail(appUser.getEmail());
        if (user.isPresent()){
            AppUser existentUser = user.stream().findFirst().get();
            if(existentUser.getEnable()){
                throw new IllegalStateException("Email Already Taken");
            }else {
                confirmationTokenService.deleteById(existentUser.getId());
                appUserRepository.delete(existentUser);
            }
        }

        final String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }
}
