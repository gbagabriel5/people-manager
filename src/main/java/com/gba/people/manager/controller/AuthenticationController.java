package com.gba.people.manager.controller;

import com.gba.people.manager.dto.LoginDto;
import com.gba.people.manager.dto.TokenDto;
import com.gba.people.manager.service.TokenService;
import com.gba.people.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @PostMapping
    public TokenDto authenticate(@RequestBody LoginDto login) {
        userService.initializeDatabase();
        UsernamePasswordAuthenticationToken loginData = login.convert();
        Authentication authentication = manager.authenticate(loginData);
        String token = tokenService.generateToken(authentication);
        return new TokenDto(token, "Bearer");
    }
}
