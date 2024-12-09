package com.helmes.assignment.config;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

        String redirectUrl = request.getContextPath();

        if(authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            redirectUrl = "/admin/home";
        } else if(authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER"))) {
            redirectUrl = "/user/home";
        }
        response.sendRedirect(redirectUrl);

    }
}
