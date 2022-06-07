package com.gba.people.manager.service.impl;

import com.gba.people.manager.model.User;
import com.gba.people.manager.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.Date;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static java.lang.Long.parseLong;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${peoplemanager.jwt.expiration}")
    private String expiration;

    @Value("${peoplemanager.jwt.secret}")
    private String secret;

    @Override
    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + parseLong(expiration));
        return Jwts.builder()
                .setIssuer("Restaurant API")
                .setSubject(user.getId().toString())
                .setIssuedAt(today)
                .setExpiration(expirationDate)
                .signWith(HS256, secret)
                .compact();
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Integer getIdUser(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return Integer.parseInt(claims.getSubject());
    }
}