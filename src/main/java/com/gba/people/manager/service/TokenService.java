package com.gba.people.manager.service;

import org.springframework.security.core.Authentication;

public interface TokenService {
    String generateToken(Authentication authentication);

    boolean isTokenValid(String token);

    Integer getIdUser(String token);
}
