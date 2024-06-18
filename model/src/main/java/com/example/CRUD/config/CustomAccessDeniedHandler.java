package com.example.CRUD.config;

import java.io.IOException;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            org.springframework.security.access.AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        Authentication authentication = (Authentication) request.getUserPrincipal();
        if (authentication != null) {
            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

            if (roles.contains("ROLE_ADMIN")) {
                response.sendRedirect("/admin/admin_profile");
            } else if (roles.contains("ROLE_CINEMA")) {
                response.sendRedirect("/cinemaowner/homecinemaowner");
            } else if (roles.contains("ROLE_USER")) {
                response.sendRedirect("/user/home");
            } else {
                response.sendRedirect("/login?error=accessDenied");
            }
        } else {
            response.sendRedirect("/login?error=accessDenied");
        }
    }
}
