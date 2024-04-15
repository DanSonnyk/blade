package com.bladebackend.blade.customer.domains;

import lombok.Getter;

@Getter
public class AuthenticationDTO {
    private String email;
    private String password;
}
