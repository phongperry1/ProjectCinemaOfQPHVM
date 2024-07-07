package com.example.CRUD.config;

import java.io.IOException;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ADMIN")) {
            response.sendRedirect("/show");
        } else if (roles.contains("CINEMA_OWNER")) {
            response.sendRedirect("/cinemaowner/homecinemaowner");
        } else if (roles.contains("USER")) {
            response.sendRedirect("/user/home");
        } else if (roles.contains("GUEST")) {
            response.sendRedirect("/guest/home_Guest");
        } else {
        }
    }
}