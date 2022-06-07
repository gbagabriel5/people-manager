package com.gba.people.manager.config.security;

import com.gba.people.manager.repository.UserRepository;
import com.gba.people.manager.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static com.google.common.base.Strings.isNullOrEmpty;

@RequiredArgsConstructor
@Component
public class AuthenticationByTokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    private final UserRepository repository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = recoverToken(request);
        boolean valid = tokenService.isTokenValid(token);
        if(valid) {
            authenticateClient(token);
        }
        filterChain.doFilter(request, response);
    }

    private void authenticateClient(String token) {
        Integer idUser = tokenService.getIdUser(token);
        repository.findById(idUser).ifPresent(user -> {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        });
    }

    private String recoverToken(HttpServletRequest request) {
        String token = request.getHeader("authorization");
        if(isNullOrEmpty(token) || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.substring(7);
    }
}
