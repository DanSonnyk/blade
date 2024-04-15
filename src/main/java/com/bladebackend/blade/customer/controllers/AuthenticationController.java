package com.bladebackend.blade.customer.controllers;

import com.bladebackend.blade.customer.domains.AppUser;
import com.bladebackend.blade.customer.domains.AuthenticationDTO;
import com.bladebackend.blade.customer.domains.LoginResponse;
import com.bladebackend.blade.customer.domains.RegistrationRequest;
import com.bladebackend.blade.customer.usecases.RegistrationService;
import com.bladebackend.blade.security.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/auth/")
public class AuthenticationController {

    private RegistrationService registrationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping(path = "registration")
    public String register(@RequestBody RegistrationRequest request ){
        return registrationService.register(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token){

        return registrationService.confirmToken(token);
    }

    @PostMapping(path = "login")
    public ResponseEntity<?> login(@RequestBody AuthenticationDTO data){
        var userNamePassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
        var auth = authenticationManager.authenticate(userNamePassword);
        var token = tokenService.generateToken((AppUser) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
